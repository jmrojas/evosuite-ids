/*
 * Copyright (C) 2010 Saarland University
 * 
 * This file is part of EvoSuite.
 * 
 * EvoSuite is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * EvoSuite is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser Public License along with
 * EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */

package de.unisb.cs.st.evosuite.cfg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import de.unisb.cs.st.evosuite.cfg.CFGGenerator.CFGVertex;

public class ControlFlowGraph {

	private static Logger logger = Logger.getLogger(ControlFlowGraph.class);

	private DirectedGraph<CFGVertex, DefaultEdge> graph = new DirectedMultigraph<CFGVertex, DefaultEdge>(
	        DefaultEdge.class);

	private int diameter = 0;

	public ControlFlowGraph(DirectedGraph<CFGVertex, DefaultEdge> cfg) {
		graph = cfg;
		setDiameter();

		// Calculate mutation distances
		logger.trace("Calculating mutation distances");
		for (CFGVertex m : cfg.vertexSet()) {
			if (m.isMutation()) {
				for (Long id : m.getMutationIds()) {
					for (CFGVertex v : cfg.vertexSet()) {
						DijkstraShortestPath<CFGVertex, DefaultEdge> d = new DijkstraShortestPath<CFGVertex, DefaultEdge>(
						        graph, v, m);
						int distance = (int) Math.round(d.getPathLength());
						if (distance >= 0)
							v.setDistance(id, distance);
						else
							v.setDistance(id, diameter);
					}
				}
			}
		}
	}

	public int getDiameter() {
		return diameter;
	}

	private void setDiameter() {
		FloydWarshall<CFGVertex, DefaultEdge> f = new FloydWarshall<CFGVertex, DefaultEdge>(
		        graph);
		diameter = (int) f.getDiameter();
	}

	public CFGVertex getMutation(long id) {
		for (CFGVertex v : graph.vertexSet()) {
			if (v.isMutation()) {
				if (v.hasMutation(id)) {
					return v;
				}
			}
		}
		return null;
	}

	public List<Long> getMutations() {
		List<Long> ids = new ArrayList<Long>();
		for (CFGVertex v : graph.vertexSet()) {
			if (v.isMutation())
				ids.addAll(v.getMutationIds());
		}
		return ids;
	}

	public boolean containsMutation(long id) {
		for (CFGVertex v : graph.vertexSet()) {
			if (v.isMutation() && v.hasMutation(id))
				return true;
		}
		return false;
	}

	private CFGVertex getBranchVertex(int number) {
		for (CFGVertex v : graph.vertexSet()) {
			if (v.isBranch() && v.getBranchId() == number) {
				return v;
			}
		}
		return null;
	}

	public CFGVertex getVertex(int id) {
		for (CFGVertex v : graph.vertexSet()) {
			if (v.id == id) {
				return v;
			}
		}
		return null;
	}

	/**
	 * Return number of control dependent branches between path and mutation
	 * 
	 * @param path
	 * @param mutation
	 * @return
	 */
	public int getControlDistance(List<Integer> path, long mutation) {
		CFGVertex m = getMutation(mutation);
		if (m == null) {
			logger.warn("Could not find mutation");
			return 0;
		}

		int min = Integer.MAX_VALUE;
		for (Integer i : path) {
			//			CFGVertex v = getBranchVertex(i);
			CFGVertex v = getVertex(i);
			if (v != null) {
				int distance = v.getDistance(mutation);
				//logger.info("Distance from "+i+": "+distance);
				if (distance < min) {
					min = distance;
				}
			} else {
				logger.warn("Could not find vertex " + i);
				for (CFGVertex vertex : graph.vertexSet()) {
					logger.warn("  -> " + vertex.toString());
				}
			}
		}

		return min;
	}

	/**
	 * Return number of control dependent branches between path and mutation
	 * 
	 * @param path
	 * @param vertex
	 * @return
	 */
	public int getControlDistance(List<Integer> path, int vertex) {
		CFGVertex m = getVertex(vertex);
		if (m == null) {
			logger.warn("Vertex does not exist in graph: " + vertex);
			for (CFGVertex v : graph.vertexSet()) {
				logger.info("  Vertex id: " + v.id + ", line number " + v.line_no
				        + ", branch id: " + v.getBranchId());
			}
			return diameter;
		}
		/*
		for(CFGVertex v : graph.vertexSet()) {
			logger.info("Graph vertex: "+v.id);
			for(DefaultEdge edge : graph.outgoingEdgesOf(v)) {
				logger.info("  -> "+graph.getEdgeTarget(edge).id);
			}
		}
		*/

		int min = Integer.MAX_VALUE;
		for (Integer i : path) {
			logger.info("Current step in path: " + i + ", looking for " + vertex);
			// FIXME: Problem: i is not a bytecode id, but a branch ID (or a line id). What can we do?
			CFGVertex v = getVertex(i);
			if (v != null) {
				// FIXME: This does not actually calculate the distance!
				//int distance = v.getDistance(vertex);
				DijkstraShortestPath<CFGVertex, DefaultEdge> d = new DijkstraShortestPath<CFGVertex, DefaultEdge>(
				        graph, v, m);
				int distance = (int) Math.round(d.getPathLength());
				logger.info("Path vertex " + i + " has distance: " + distance);
				if (distance < min && distance >= 0) {
					min = distance;
				}
			} else {
				logger.info("Could not find path vertex " + i);
			}
		}

		return min;
	}

	/**
	 * Return the distance to setting the last branch on the right track towards
	 * the mutation
	 * 
	 * @param path
	 * @param mutation
	 * @return
	 */
	public double getBranchDistance(List<Integer> path, List<Double> distances,
	        long mutation) {
		CFGVertex m = getMutation(mutation);
		if (m == null)
			return 0.0;

		int min = Integer.MAX_VALUE;
		double dist = Double.MAX_VALUE;
		for (int i = 0; i < path.size(); i++) {
			CFGVertex v = getBranchVertex(path.get(i));
			if (v != null) {
				int distance = v.getDistance(mutation);
				if (distance < min) {
					min = distance;
					dist = distances.get(i);
				}

			}
		}

		return dist;
	}

	/**
	 * Return the distance to setting the last branch on the right track towards
	 * the mutation
	 * 
	 * @param path
	 * @param mutation
	 * @return
	 */
	public double getBranchDistance(List<Integer> path, List<Double> distances,
	        int branch_id) {
		CFGVertex m = getVertex(branch_id);
		if (m == null) {
			logger.info("Could not find branch node");
			return 0.0;
		}

		int min = Integer.MAX_VALUE;
		double dist = Double.MAX_VALUE;
		for (int i = 0; i < path.size(); i++) {
			CFGVertex v = getVertex(path.get(i));
			if (v != null) {
				DijkstraShortestPath<CFGVertex, DefaultEdge> d = new DijkstraShortestPath<CFGVertex, DefaultEdge>(
				        graph, v, m);
				int distance = (int) Math.round(d.getPathLength());

				if (distance < min && distance >= 0) {
					min = distance;
					dist = distances.get(i);
					logger.info("B: Path vertex " + i + " has distance: " + distance
					        + " and branch distance " + dist);
				}
			} else {
				logger.info("Path vertex does not exist in graph");
			}
		}

		return dist;
	}

	public boolean isSuccessor(CFGVertex v1, CFGVertex v2) {
		return (graph.containsEdge(v1, v2) && graph.inDegreeOf(v2) == 1);
	}

	public int getDistance(CFGVertex v1, CFGVertex v2) {
		DijkstraShortestPath<CFGVertex, DefaultEdge> d = new DijkstraShortestPath<CFGVertex, DefaultEdge>(
		        graph, v1, v2);
		return (int) Math.round(d.getPathLength());
	}

	public int getInitialDistance(CFGVertex v) {
		int minimum = diameter;

		for (CFGVertex node : graph.vertexSet()) {
			if (graph.inDegreeOf(node) == 0) {
				DijkstraShortestPath<CFGVertex, DefaultEdge> d = new DijkstraShortestPath<CFGVertex, DefaultEdge>(
				        graph, node, v);
				int distance = (int) Math.round(d.getPathLength());
				if (distance < minimum)
					minimum = distance;
			}
		}
		return minimum;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (DefaultEdge e : graph.edgeSet()) {
			sb.append(graph.getEdgeSource(e) + " -> " + graph.getEdgeTarget(e));
			sb.append("\n");
		}
		return sb.toString();
	}

	public void toDot(String filename) {

		try {

			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			if (!graph.vertexSet().isEmpty()) {
				//FrameVertexNameProvider nameprovider = new FrameVertexNameProvider(mn.instructions);
				//	DOTExporter<Integer,DefaultEdge> exporter = new DOTExporter<Integer,DefaultEdge>();
				//DOTExporter<Integer,DefaultEdge> exporter = new DOTExporter<Integer,DefaultEdge>(new IntegerNameProvider(), nameprovider, new IntegerEdgeNameProvider());
				//			DOTExporter<Integer,DefaultEdge> exporter = new DOTExporter<Integer,DefaultEdge>(new LineNumberProvider(), new LineNumberProvider(), new IntegerEdgeNameProvider());
				DOTExporter<CFGVertex, DefaultEdge> exporter = new DOTExporter<CFGVertex, DefaultEdge>(
				        new IntegerNameProvider<CFGVertex>(),
				        new StringNameProvider<CFGVertex>(),
				        new IntegerEdgeNameProvider<DefaultEdge>());
				exporter.export(out, graph);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<CFGVertex> getUsesForDef(CFGVertex def) {

		if (!def.isDefinition())
			throw new IllegalArgumentException("method expects a field definition");
		if (!graph.containsVertex(def))
			throw new IllegalArgumentException("vertex not in graph");

		return getUsesForDef(def.getDUVariableName(), def);
	}

	public boolean hasDefClearPathToMethodEnd(CFGVertex duVertex) {

		if (!duVertex.isDU())
			throw new IllegalArgumentException("method expects a du vertex");
		if (!graph.containsVertex(duVertex))
			throw new IllegalArgumentException("vertex not in graph");

		return hasDefClearPathToMethodEnd(duVertex.getDUVariableName(), duVertex);
	}

	public boolean hasDefClearPathFromMethodStart(CFGVertex duVertex) {

		if (!duVertex.isDU())
			throw new IllegalArgumentException("method expects a du vertex");
		if (!graph.containsVertex(duVertex))
			throw new IllegalArgumentException("vertex not in graph");

		return hasDefClearPathFromMethodStart(duVertex.getDUVariableName(), duVertex);
	}

	private List<CFGVertex> getUsesForDef(String varName, CFGVertex currentVertex) {

		if (!graph.containsVertex(currentVertex))
			throw new IllegalArgumentException("vertex not in graph");

		ArrayList<CFGVertex> r = new ArrayList<CFGVertex>();

		Set<DefaultEdge> outgoingEdges = graph.outgoingEdgesOf(currentVertex);
		if (outgoingEdges.size() == 0)
			return r;

		for (DefaultEdge e : outgoingEdges) {

			CFGVertex edgeTarget = graph.getEdgeTarget(e);
			if (edgeTarget.isDefinition()
			        && edgeTarget.getDUVariableName().equals(varName)) {
				continue;
			}
			if (edgeTarget.isUse() && edgeTarget.getDUVariableName().equals(varName)) {
				r.add(edgeTarget);
			}
			if (edgeTarget.id > currentVertex.id) // dont follow backedges (loops)
				r.addAll(getUsesForDef(varName, edgeTarget));

		}

		return r;
	}

	private boolean hasDefClearPathToMethodEnd(String varName, CFGVertex currentVertex) {

		if (!graph.containsVertex(currentVertex))
			throw new IllegalArgumentException("vertex not in graph");

		Set<DefaultEdge> outgoingEdges = graph.outgoingEdgesOf(currentVertex);
		if (outgoingEdges.size() == 0)
			return true;

		for (DefaultEdge e : outgoingEdges) {

			CFGVertex edgeTarget = graph.getEdgeTarget(e);

			// skip edges going into another def for the same field
			if (edgeTarget.isDefinition()) {
				if (edgeTarget.getDUVariableName().equals(varName))
					continue;
			}

			if (edgeTarget.id > currentVertex.id // dont follow backedges (loops)
			        && hasDefClearPathToMethodEnd(varName, edgeTarget))
				return true;
		}

		return false;
	}

	private boolean hasDefClearPathFromMethodStart(String varName, CFGVertex currentVertex) {

		if (!graph.containsVertex(currentVertex))
			throw new IllegalArgumentException("vertex not in graph");

		Set<DefaultEdge> incomingEdges = graph.incomingEdgesOf(currentVertex);
		if (incomingEdges.size() == 0)
			return true;

		for (DefaultEdge e : incomingEdges) {

			CFGVertex edgeStart = graph.getEdgeSource(e);

			// skip edges coming from a def for the same field
			if (edgeStart.isDefinition()) {
				if (edgeStart.getDUVariableName().equals(varName))
					continue;
			}

			if (edgeStart.id < currentVertex.id // dont follow backedges (loops) 
			        && hasDefClearPathFromMethodStart(varName, edgeStart))
				return true;
		}

		return false;
	}

	/**
	 * @param branchVertex
	 */
	public void markBranchIDs(CFGVertex branchVertex) {

		if (!branchVertex.isBranch())
			throw new IllegalArgumentException("branch vertex expected");

		if (branchVertex.branchID == -1)
			throw new IllegalArgumentException("expect branchVertex to have branchID set");

		Set<DefaultEdge> out = graph.outgoingEdgesOf(branchVertex);

		if (out.size() < 2)
			throw new IllegalStateException(
			        "expect branchVertices to have exactly two outgoing edges");

		int minID = Integer.MAX_VALUE;
		int maxID = Integer.MIN_VALUE;

		for (DefaultEdge e : out) {
			CFGVertex target = graph.getEdgeTarget(e);
			if (minID > target.id)
				minID = target.id;
			if (maxID < target.id)
				maxID = target.id;
		}

		if (minID < branchVertex.id) {
			//			System.out.println("DO-WHILE BRANCH"+branchVertex.branchID);
			return;
		}

		markNodes(minID, maxID, branchVertex.branchID, true);

		if (isIfBranch(maxID)) {
			//			System.out.println("IF BRANCH: "+branchVertex.branchID);
			CFGVertex prevVertex = getVertex(maxID - 1);
			if (prevVertex.isGoto()) {
				//				System.out.println("WITH ELSE PART");
				Set<DefaultEdge> prevOut = graph.outgoingEdgesOf(prevVertex);
				if (prevOut.size() != 1)
					throw new IllegalStateException(
					        "expect gotos to only have 1 outgoing edge");
				DefaultEdge elseEnd = null;
				for (DefaultEdge e : prevOut)
					elseEnd = e;
				markNodes(maxID + 1, graph.getEdgeTarget(elseEnd).id,
				          branchVertex.branchID, false);

			}
		}

	}

	private void markNodes(int start, int end, int branchID, boolean branchExpressionValue) {
		for (int i = start; i <= end; i++) {
			CFGVertex v = getVertex(i);
			if (v != null) {
				v.branchID = branchID;
				v.branchExpressionValue = branchExpressionValue;
			}
		}
	}

	private boolean isIfBranch(int maxID) {

		CFGVertex prevVertex = getVertex(maxID - 1);

		Set<DefaultEdge> prevOut = graph.outgoingEdgesOf(prevVertex);
		if (prevOut.size() != 1) {
			return false;
		}

		DefaultEdge backEdge = null;
		for (DefaultEdge e : prevOut)
			backEdge = e;

		return !(graph.getEdgeTarget(backEdge).id < maxID);
	}

}
