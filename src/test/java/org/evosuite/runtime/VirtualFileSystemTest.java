package org.evosuite.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.evosuite.mock.java.io.MockFile;
import org.evosuite.mock.java.io.MockFileInputStream;
import org.evosuite.mock.java.io.MockFileOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class VirtualFileSystemTest {

	@Before
	public void init(){
		VirtualFileSystem.getInstance().resetSingleton();
		VirtualFileSystem.getInstance().init();
	}
	
	@After
	public void tearDown(){
		VirtualFileSystem.getInstance().resetSingleton();
	}
	
	@Test
	public void testReadAfterWriteToFile() throws IOException{
		
		File file = MockFile.createTempFile("foo", ".tmp");
		Assert.assertTrue(file.exists());
		
		byte[] data = new byte[]{42,66};
		MockFileOutputStream out = new MockFileOutputStream(file);
		out.write(data);
		out.close();
		
		MockFileInputStream in = new MockFileInputStream(file);
		byte[] buffer = new byte[4];
		int count = in.read(buffer);
		in.close();
		Assert.assertEquals("End of stream should had been reached",-1, count);
		Assert.assertEquals(data[0],buffer[0]);
		Assert.assertEquals(data[1],buffer[1]);
		Assert.assertEquals(0,buffer[2]);
		Assert.assertEquals(0,buffer[3]);
	}
	
	@Test
	public void testWriteToFile() throws IOException{
		
		File file = new MockFile("foo");
		Assert.assertFalse(file.exists());
		
		byte[] data = new byte[]{42};
		MockFileOutputStream out = new MockFileOutputStream(file);
		out.write(data);
		
		//writing to such file should create it
		Assert.assertTrue(file.exists());
		
		out.close();
		try{
			out.write(data);
			Assert.fail();
		} catch(Exception e){
			//this is expected, as the stream is closed
		}
	}
	
	@Test
	public void testTmpFileCreation() throws IOException{
		
		File file = MockFile.createTempFile("foo", ".tmp");
		Assert.assertTrue(file.exists());
		String path = file.getAbsolutePath();
		java.lang.System.out.println(path);
		Assert.assertTrue(path,path.contains("foo") & path.contains(".tmp"));
	}
	
	@Test
	public void testWorkingDirectoryExists(){
		MockFile workingDir = new MockFile(java.lang.System.getProperty("user.dir"));
		Assert.assertTrue(workingDir.exists());
	}
	
	@Test
	public void testCreateDeleteFileDirectly() throws IOException{
		
		MockFile file = new MockFile("foo");
		Assert.assertFalse(file.exists());
		boolean created = file.createNewFile();
		Assert.assertTrue(created);
		Assert.assertTrue(file.exists());
		boolean deleted = file.delete();
		Assert.assertTrue(deleted);
		Assert.assertFalse(file.exists());
	}


	@Test
	public void testCreateDeleteFolderDirectly() throws IOException{
		
		MockFile folder = new MockFile("foo"+File.separator+"hello");
		Assert.assertFalse(folder.exists());
		boolean created = folder.mkdir(); // parent doesn't exist, so should fail
		Assert.assertFalse(created);		
		Assert.assertFalse(folder.exists());
		
		created = folder.mkdirs();
		Assert.assertTrue(created);		
		Assert.assertTrue(folder.exists());
		
		MockFile file = new MockFile(folder.getAbsoluteFile()+File.separator+"evo");
		created = file.createNewFile();
		Assert.assertTrue(created);		
		Assert.assertTrue(file.exists());
		
		//deleting non-empty folder should fail
		boolean deleted = folder.delete();
		Assert.assertFalse(deleted);
		Assert.assertTrue(folder.exists());
		
		deleted = file.delete();
		Assert.assertTrue(deleted);
		Assert.assertFalse(file.exists());
		
		//now we can delete the folder
		deleted = folder.delete();
		Assert.assertTrue(deleted);
		Assert.assertFalse(folder.exists());		
	}

}
