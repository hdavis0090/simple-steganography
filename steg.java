import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;;
import java.io.File;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;

public class Processor{

	public String filepath;
	public String message;
	public Path file;
	public byte[] fileData;

	public Processor(String filepath, String message)
	{
		this.filepath = filepath;
		this.message = message;
		file = Paths.get(filepath);
		try {
			fileData = Files.readAllBytes(file);
			}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		if(!new File(filepath).exists())
		{
			System.out.println("File does not exist");
			System.exit(0);
		}
		writeTo();
	}
	
	public Processor(String filepath)
	{
		this.filepath = filepath;
		file = Paths.get(filepath);
		try {
			fileData = Files.readAllBytes(file);
			}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		if(!new File(filepath).exists())
		{
			System.out.println("File does not exist");
			System.exit(0);
		}
		readFrom();
	}
	
	void readFrom()
	{
		int offset = fileData[10];
		if(offset==54)
		{
			System.out.println("There is no secret message in this file.");
		}
		for(int i = 55; i<=offset; i++)
			System.out.print((char)fileData[i]);
	}
	
	
	void writeTo()
	{

		int messageLength = message.length();
		int copyLen = messageLength + fileData.length;
		byte[] copy = new byte[copyLen];
		
		 int offset = fileData[10];
		for(int i=0; i<offset; i++)
		{
			copy[i] = fileData[i];
		}
		
		int newOffset = offset + messageLength;
		copy[10] = (byte)(newOffset);
		
		int j = newOffset;
		for(int i=offset; i<fileData.length; i++)
		{
			copy[j] = fileData[i];
			j += 1;
		}
		
		j=0;
		for(int i=offset+1; i<=newOffset; i++)
		{
			copy[i] = (byte)message.charAt(j);
			j += 1;
		}
		
		try {
            FileOutputStream out = new FileOutputStream(filepath);
            out.write(copy);
            out.close();
			}
		catch (Exception e) {
            System.out.println(e);
			}

	}
}