package com.kafkastuff.wordcount;

import com.kafkastuff.wordcount.Container;

public class DummyB{
	public static void main(String[] args) throws InterruptedException{
		Thread.sleep(500);
		float temp = Container.getInstance().x;
		System.out.println(temp);
	}
}
