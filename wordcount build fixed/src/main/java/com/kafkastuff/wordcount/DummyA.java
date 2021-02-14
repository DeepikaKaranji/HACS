package com.kafkastuff.wordcount;

import com.kafkastuff.wordcount.Container;

public class DummyA{
	public static void main(String[] args) throws InterruptedException{
		Container.getInstance().x = 3;
		System.out.println("Value: "+Container.getInstance().x);
		Thread.sleep(30000);
	}
}
