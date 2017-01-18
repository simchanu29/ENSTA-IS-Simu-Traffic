package simEntity.Monitor;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import simEntity.Carrefour.CarrefourNames;

public class TestProba {
	static MoreRandom random = new MoreRandom(MoreRandom.globalSeed);
	static int p1=0;
	static int p2=0;
	static int p3=0;
	static int p4=0;
	static int p5=0;
	static int p6=0;
	static int p7=0;
	static CarrefourNames calculDestination(CarrefourNames departure){
			
		CarrefourNames destination=null;
		double pDest = random.nextDouble()*100;
		System.out.println(pDest);
		switch(departure){
			case P1:
				if(pDest<=5){ destination=CarrefourNames.P2; p2++;}
				if(pDest>5 && pDest<=15){ destination=CarrefourNames.P3; p3++;}
				if(pDest>15 && pDest<=25) {destination=CarrefourNames.P4; p4++;}
				if(pDest>25 && pDest<=30) {destination=CarrefourNames.P5;p5++;}
				if(pDest>30 && pDest<=65) {destination=CarrefourNames.P6;p6++;}
				if(pDest>65 && pDest<=100){ destination=CarrefourNames.P7;p7++;}
				break;
			case P2:
				if(pDest<=10) {destination=CarrefourNames.P1; p1++;}
				if(pDest>10 && pDest<=15){ destination=CarrefourNames.P3;p3++;}
				if(pDest>15 && pDest<=35) {destination=CarrefourNames.P4;p4++;}
				if(pDest>35 && pDest<=55) {destination=CarrefourNames.P5;p5++;}
				if(pDest>55 && pDest<=80) {destination=CarrefourNames.P6;p6++;}
				if(pDest>80 && pDest<=100) {destination=CarrefourNames.P7;p7++;}
				break;
			case P3:
				if(pDest<=15) destination=CarrefourNames.P1;
				if(pDest>15 && pDest<=30) destination=CarrefourNames.P2;
				if(pDest>30 && pDest<=50) destination=CarrefourNames.P4;
				if(pDest>50 && pDest<=70) destination=CarrefourNames.P5;
				if(pDest>70 && pDest<=90) destination=CarrefourNames.P6;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P4:
				if(pDest<=15) destination=CarrefourNames.P1;
				if(pDest>15 && pDest<=25) destination=CarrefourNames.P2;
				if(pDest>25 && pDest<=35) destination=CarrefourNames.P3;
				if(pDest>35 && pDest<=55) destination=CarrefourNames.P5;
				if(pDest>55 && pDest<=95) destination=CarrefourNames.P6;
				if(pDest>95 && pDest<=100) destination=CarrefourNames.P7;
				break;
			case P5:
				if(pDest<=10) destination=CarrefourNames.P1;
				if(pDest>10 && pDest<=40) destination=CarrefourNames.P2;
				if(pDest>40 && pDest<=50) destination=CarrefourNames.P3;
				if(pDest>50 && pDest<=60) destination=CarrefourNames.P4;
				if(pDest>60 && pDest<=70) destination=CarrefourNames.P6;
				if(pDest>70 && pDest<=100) destination=CarrefourNames.P7;
				p5++;
				break;
			case P6:
				if(pDest<=20) destination=CarrefourNames.P1;
				if(pDest>20 && pDest<=30) destination=CarrefourNames.P2;
				if(pDest>30 && pDest<=70) destination=CarrefourNames.P3;
				if(pDest>70 && pDest<=80) destination=CarrefourNames.P4;
				if(pDest>80 && pDest<=90) destination=CarrefourNames.P5;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P7;
				p6++;
				break;
			case P7:
				if(pDest<=20) destination=CarrefourNames.P1;
				if(pDest>20 && pDest<=40) destination=CarrefourNames.P2;
				if(pDest>40 && pDest<=60) destination=CarrefourNames.P3;
				if(pDest>60 && pDest<=80) destination=CarrefourNames.P4;
				if(pDest>80 && pDest<=90) destination=CarrefourNames.P5;
				if(pDest>90 && pDest<=100) destination=CarrefourNames.P6;
				p7++;
				break;
		default:
			break;
				
		}
		
		return destination;
	}
	public static void main (String[] args){
		LogicalDateTime logicalDate=new LogicalDateTime("10/12/2016 10:34:47.6789");
		System.out.println(logicalDate);
		System.out.println(logicalDate.getHour());
		
		
	}
}
