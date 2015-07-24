package com.aol.mineusb.utils;

import java.io.File;
import java.io.PrintWriter;

import com.aol.mineusb.MineUSB;
import com.aol.w67clement.mineapi.logger.MineLogger;

public class FileUtils {
	
	private static MineLogger console = MineUSB.getConsole();
	
	public static void generateRunLauncherScriptWindows(File file, String title, File runFile) {
		PrintWriter output = null;
		try {
			if (file.exists()) {
				file.delete();
			}
			output = new PrintWriter(file);
			console.info("=======================[" + file.getName() + "]========================");
			output.println("@echo off");
			console.info("@echo off");
			output.println("title " + title);
			console.info("title " + title);
			output.println("@echo Setting the APPDATA Path....");
			console.info("@echo Setting the APPDATA Path....");
			output.println("set APPDATA=%CD%\\data");
			console.info("set APPDATA=%CD%\\data");
			output.println("@echo Running...");
			console.info("@echo Running...");
			output.println("start bin\\" + runFile.getName());
			console.info("start bin\\" + runFile.getName());
			output.println("@echo Finish!");
			console.info("@echo Finish!");
			output.println("exit");
			console.info("exit");
			console.info("=======================[" + file.getName() + "]========================");
			output.close();
		} catch (Throwable e) {

		}
	}
	
	public static void generateRunJavaLauncherScriptWindows(File file, String title, File runFile) {
		PrintWriter output = null;
		try {
			if (file.exists()) {
				file.delete();
			}
			output = new PrintWriter(file);
			console.info("=======================[" + file.getName() + "]========================");
			output.println("@echo off");
			console.info("@echo off");
			output.println("title " + title);
			console.info("title " + title);
			output.println("@echo Setting the APPDATA Path....");
			console.info("@echo Setting the APPDATA Path....");
			output.println("set APPDATA=%CD%\\data");
			console.info("set APPDATA=%CD%\\data");
			output.println("@echo Running...");
			console.info("@echo Running...");
			output.println("java -jar bin\\" + runFile.getName());
			console.info("java -jar bin\\" + runFile.getName());
			output.println("@echo Finish!");
			console.info("@echo Finish!");
			output.println("exit");
			console.info("exit");
			console.info("=======================[" + file.getName() + "]========================");
			output.close();
		} catch (Throwable e) {

		}
	}
	
	public static void generateRunLauncherScriptMac(File file, String title, File runFile) {
		PrintWriter output = null;
		try {
			if (file.exists()) {
				file.delete();
			}
			output = new PrintWriter(file);
			console.info("=======================[" + file.getName() + "]========================");
			output.println("#!/bin/sh ");
			console.info("#!/bin/sh ");
			output.println("cd \"'dirname \"$0\"'\" ");
			console.info("cd \"'dirname \"$0\"'\" ");
			output.println("export HOME=$(perl -MCwd=realpath -e \"print realpath './data'\") ");
			console.info("export HOME=$(perl -MCwd=realpath -e \"print realpath './data'\") ");
			output.println("java -jar ./bin/" + runFile.getName());
			console.info("java -jar ./bin/" + runFile.getName());
			console.info("=======================[" + file.getName() + "]========================");
			output.close();
		} catch (Throwable e) {

		}
	}

	public static void generateRunLauncherScriptLinux(File file, String title, File runFile) {
		PrintWriter output = null;
		try {
			if (file.exists()) {
				file.delete();
			}
			output = new PrintWriter(file);
			console.info("=======================[" + file.getName() + "]========================");
			output.println("#!/bin/bash ");
			console.info("#!/bin/bash ");
			output.println("export MINECRAFTDATA=\"'readlink -f ./data'\"");
			console.info("export MINECRAFTDATA=\"'readlink -f ./data'\"");
			output.println("java -Duser.home=$MINECRAFTDATA -jar ./bin/" + runFile.getName());
			console.info("java -Duser.home=$MINECRAFTDATA -jar ./bin/" + runFile.getName());
			console.info("=======================[" + file.getName() + "]========================");
			output.close();
		} catch (Throwable e) {

		}
	}
}
