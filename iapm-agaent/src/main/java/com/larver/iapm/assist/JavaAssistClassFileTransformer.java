package com.larver.iapm.assist;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtClass;
import sun.management.snmp.jvmmib.JvmClassLoadingMBean;
import sun.management.snmp.jvmmib.JvmClassLoadingMeta;

/**
 * @ClassName: JavaAssistClassFileTransformer
 * @Descraption: TODO
 * @Author: kongxiangyun
 * @Date: 2019/8/19 17:13
 * @Version 1.0
 **/
public class JavaAssistClassFileTransformer implements ClassFileTransformer {

	private ClassPool classPool = ClassPool.getDefault();

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
		ProtectionDomain protectionDomain, byte[] classfileBuffer)
		throws IllegalClassFormatException {

		System.out.println("Tranforming start "+ className);

		CtClass ctClass = null;

		byte[] returnByte = null;

		JvmClassLoadingMeta
		String classNameJava = JvmClassLoadingMBean

		return new byte[0];
	}
}
