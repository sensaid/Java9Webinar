package com.vijfhart.cursus.democlient;

import com.vijfhart.cursus.demo.Demo;

//import java.util.logging.Logger;


public class Main {
  //private  static final Logger logger = Logger.getLogger("Main.class");
  public static void main(String [] args) {
    Demo demo = new Demo();
    System.out.println(demo.getMessage());
	//logger.info(demo.getMessage());
  }
}
