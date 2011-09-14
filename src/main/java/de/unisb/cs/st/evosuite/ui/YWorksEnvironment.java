package de.unisb.cs.st.evosuite.ui;

import java.awt.FontMetrics;
import java.awt.Label;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import y.base.Edge;
import y.base.Node;
import y.io.GraphMLIOHandler;
import y.view.*;
import y.view.hierarchy.GroupNodeRealizer;
import y.view.hierarchy.HierarchyManager;

public class YWorksEnvironment {
	private final class AdjustSizeToLabelListener implements Graph2DListener {
		public final static int INDENTATION = 2; 
		
		@Override
		public void onGraph2DEvent(Graph2DEvent e) {
			Node v = null;
			String p = e.getPropertyName();

			if (e.getSubject() instanceof NodeLabel && "text".equals(p)) {
				v = ((NodeLabel) e.getSubject()).getNode();
			} else if (e.getSubject() instanceof Node && "realizer".equals(p)) {
				v = (Node) e.getSubject();
			}

			if (v != null) {
				adjustNodeSize(YWorksEnvironment.this.graph.getRealizer(v));
			}
		}

		private void adjustNodeSize(NodeRealizer realizer) {
			NodeLabel label = realizer.getLabel();
			FontMetrics fm = new Label().getFontMetrics(label.getFont());
			String labelText = label.getText();

			// find max needed width
			Pattern pat = Pattern.compile("(.*)", Pattern.MULTILINE);
			Matcher matcher = pat.matcher(labelText);
			int maxWidth = 0;
			while (matcher.find()) {
				String currentLine = matcher.group();
				int currentWidth = fm.stringWidth(currentLine);
				if (currentWidth > maxWidth) {
					maxWidth = currentWidth;
				}
			}
			if (maxWidth > 0) {
				realizer.setWidth(maxWidth + 2 * INDENTATION);
			} else {// fallback width if no label is set
				realizer.setWidth(YWorksEnvironment.this.graph.getDefaultNodeRealizer().getWidth());
			}

			// find max needed height
			int lineCount = 1;
			for (Matcher m = Pattern.compile("\n").matcher(labelText); m.find();) {
				lineCount++;
			}
			int lineHeight = fm.getHeight();
			if (labelText.length() > 0) {
				realizer.setHeight(lineHeight * lineCount + 2 * INDENTATION);
			} else { // fallback height if no label is set
				realizer.setHeight(YWorksEnvironment.this.graph.getDefaultNodeRealizer().getHeight());
			}
		}
	}

	private Graph2D graph;
	private Map<Object, Node> objToNode;
	private GraphMLIOHandler graphMLIOHandler;
	private LinkedList<Node> groupStack;

	private HierarchyManager hierarchyManager;

	public YWorksEnvironment() {
		this.clear();
	}

	public void clear() {
		this.graph = new Graph2D();
		this.graph.addGraph2DListener(new AdjustSizeToLabelListener());

		this.hierarchyManager = new HierarchyManager(this.graph);
		this.graph.setHierarchyManager(this.hierarchyManager);

		this.graphMLIOHandler = new GraphMLIOHandler();

		this.objToNode = new HashMap<Object, Node>();
		this.groupStack = new LinkedList<Node>();
	}

	public Graph2D getGraph() {
		return this.graph;
	}

	public NodeRealizer realizer(Node node) {
		return this.graph.getRealizer(node);
	}

	public EdgeRealizer realizer(Edge edge) {
		return this.graph.getRealizer(edge);
	}

	public synchronized Node getNodeFor(Object obj) {
		if (!this.objToNode.containsKey(obj)) {
			Node result = this.graph.createNode();

			Node currentGroupNode = this.currentGroupNode();
			if (currentGroupNode != null) {
				this.setParentNode(result, currentGroupNode);
			}
			
			this.objToNode.put(obj, result);
		}

		return this.objToNode.get(obj);
	}

	public NodeRealizer getNodeRealizerFor(Object obj) {
		return this.realizer(this.getNodeFor(obj));
	}

	public Node getGroupNodeFor(Object obj) {
		Node parentNode = this.currentGroupNode();

		if (parentNode == null)
			parentNode = this.hierarchyManager.getAnchorNode(this.graph);

		Node result = this.getGroupNodeFor(obj, parentNode);
		return result;
	}

	public synchronized Node getGroupNodeFor(Object obj, Node parent) {
		if (!this.objToNode.containsKey(obj)) {
			Node result = this.hierarchyManager.createGroupNode(parent);
			this.hierarchyManager.setParentNode(result, parent);

			NodeRealizer realizer = this.graph.getRealizer(result);

			if (realizer instanceof GroupNodeRealizer) {
				((GroupNodeRealizer) realizer).setConsiderNodeLabelSize(true);
			} else {
				System.out.println("Unknown group node realizer: " + realizer.getClass());
			}

			this.objToNode.put(obj, result);
		}

		return this.objToNode.get(obj);
	}

	public void writeGraphML(OutputStream outputStream) throws IOException {
		this.graphMLIOHandler.write(this.graph, outputStream);
	}

	public void pushGroupNode(Node node) {
		this.groupStack.push(node);
	}

	public Node pushGroupNodeFor(Object obj) {
		this.groupStack.push(this.getGroupNodeFor(obj));
		return this.getGroupNodeFor(obj);
	}

	public NodeRealizer realizerPushGroupNodeFor(Object obj) {
		return this.realizer(this.pushGroupNodeFor(obj));
	}

	public void popGroupNode() {
		this.groupStack.pop();
	}

	public Node currentGroupNode() {
		return this.groupStack.peek();
	}

	public void setParentNode(Node node, Node parent) {
		assert (node != null);
		this.hierarchyManager.setParentNode(node, parent);
	}

	public EdgeRealizer getEdgeRealizerFor(Object from, Object to) {
		Edge edge = this.graph.createEdge(this.getNodeFor(from), this.getNodeFor(to));
		return this.realizer(edge);
	}
}
