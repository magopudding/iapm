package com.larver.iapm.main;

import com.larver.iapm.assist.JavassistClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @ClassName: JavaAgaentmain
 * @Descraption: TODO
 * @Author: kongxiangyun
 * @Date: 2019/8/19 17:12
 * @Version 1.0
 **/
public class JavaAgentmain {

	public static void premain(String args, Instrumentation instr){
		System.out.println("Args is "+ args);
		System.out.println("Begin to retansformClasses");
		instr.addTransformer(new JavassistClassFileTransformer());
	}

	public static void main(String[] args) {
		
	}
}
