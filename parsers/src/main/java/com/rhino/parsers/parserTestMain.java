package com.rhino.parsers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class parserTestMain
{
	public static void main(String[] args)
	{
		InputStream is = null;

		try
		{
			is = new BufferedInputStream(new FileInputStream(new File("src/main/resources/sample.txt")));

			Parser parser = new AutoDetectParser();
			ContentHandler handler = new BodyContentHandler(System.out);

			Metadata metadata = new Metadata();

			parser.parse(is, handler, metadata, new ParseContext());

			for (String name : metadata.names())
			{
				String value = metadata.get(name);

				if (value != null)
				{
					System.out.println("Metadata Name:  " + name);
					System.out.println("Metadata Value: " + value);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	
	
}
