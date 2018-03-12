package org.zjw.blog.util.file;

import java.io.*;


/**
 * 文件操作类
 * @author Administrator
 *
 */
public class FileIO {
	
	/**
	 * 读文本文件
	 * @param path 源文件路径
	 * @return 字符串
	 * @throws Exception 
	 */
	public static String read(String path) throws Exception{
		
		//文件对象
		File file =new File(path);
		//如果源文件不存在就抛异常
		if (!file.exists()) {
			throw new Exception(path+"不存在");
		}else{
			//字符流读取器对象
			FileReader fReader =new FileReader(file);
			//字符流缓冲模式读取器对象
			BufferedReader bReader =new BufferedReader(fReader);
			//字符缓冲对象
			StringBuffer sb=new StringBuffer();
			
			//循环一行一行的读
			String line=null;
			while((line=bReader.readLine())!=null){
				sb.append(line+"\n");
			}
			//关闭资源
			bReader.close();
			fReader.close();
			//返回字符窜
			return sb.toString();
		}
	}
	/**
	 * 写文本文件
	 * @param msg 待写入的字符串
	 * @param path 目标文件
	 * @throws Exception 
	 */
	public static void write(String msg,String path) throws Exception{
		
		//文件对象
		File file =new File(path);
		
		//字符流写入器对象
		FileWriter fWriter=new FileWriter(file);
		//字符流缓冲模式写入器对象
		BufferedWriter bWriter=new BufferedWriter(fWriter);
		//直接全部写入
		bWriter.write(msg);
		//刷新缓冲区数据,关闭资源
		bWriter.flush();
		fWriter.close();
		
	}
	//字符文件追加一行
	public static void appendLine(String path,String line) throws Exception{
		write(read(path)+"\n"+line, path);
	}
	/**
	 * 读写二进制文件
	 * @param fromPath 源文件路径
	 * @param toPath 目标文件路径
	 * @throws Exception 
	 */
	public static void binaryIO(String fromPath,String toPath) throws Exception{
		
		//源文件对象
		File fromFile=new File(fromPath);
		//目标文件对象
		File toFile=new File(toPath);
		//如果源文件不存在,抛异常
		if (!fromFile.exists()) {
			throw new Exception(fromPath+"不存在");
		}

		//字节流读取器对象
		FileInputStream fiStream=new FileInputStream(fromFile);
		//字节流缓冲模式读取器对象
		BufferedInputStream biStream =new BufferedInputStream(fiStream);
		//数据读取器对象
		DataInputStream diStream =new DataInputStream(biStream);
		
		//字节流写入器对象
		FileOutputStream foStream =new FileOutputStream(toFile);
		//字节流缓冲模式写入器对象
		BufferedOutputStream boStream =new BufferedOutputStream(foStream);
		//数据写入器对象
		DataOutputStream doStream =new DataOutputStream(boStream);
		
		//循环读取到字节数组中=>将字节数组中的数据写入到目标路径中
		int data=-1;
		byte[] bytes=new byte[1024];
		while ((data=diStream.read(bytes))!=-1) {
			doStream.write(bytes,0,data);
		}
		//关闭资源
		doStream.close();
		boStream.close();
		foStream.close();
		diStream.close();
		biStream.close();
		fiStream.close();
	}
	/**
	 * 服务器文件下载
	 * @param fiStream 服务器上的输入流
	 * @param foStream 输出到客户端的输出流
	 * @throws Exception 
	 */
	/*public static void binaryIO(FileInputStream fiStream, ServletOutputStream foStream) throws Exception{
		
		//字节流缓冲模式读取器对象
		BufferedInputStream biStream =new BufferedInputStream(fiStream);
		//数据读取器对象
		DataInputStream diStream =new DataInputStream(biStream);
		
		//字节流缓冲模式写入器对象
		BufferedOutputStream boStream =new BufferedOutputStream(foStream);
		//数据写入器对象
		DataOutputStream doStream =new DataOutputStream(boStream);
		
		//循环读取到字节数组中=>将字节数组中的数据写入到目标路径中
		int data=-1;
		byte[] bytes=new byte[1024];
		while ((data=diStream.read(bytes))!=-1) {
			doStream.write(bytes,0,data);
		}
		//关闭资源
		doStream.close();
		boStream.close();
		foStream.close();
		diStream.close();
		biStream.close();
		fiStream.close();
	}*/
}
