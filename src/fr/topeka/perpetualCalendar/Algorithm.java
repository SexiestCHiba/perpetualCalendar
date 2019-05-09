package fr.topeka.perpetualCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Quentin Legot
 *
 */
public class Algorithm {

	private int baseYear, baseMonth, baseDay, baseWeekDay, year, month, day, result;
	// baseYear, baseMonth et baseDay correspondent à la date d'aujourd'hui
	// year, month et day correspondent à la date que cherche l'utilisateur
	private List<String> weekDayList= new ArrayList<>();
	
	public Algorithm() {
		Scanner sc = new Scanner(System.in);
		// on crée une liste sur laquelle, on stocke tout les jours de la semaine
		weekDayList.add("Dimanche");
		weekDayList.add("Lundi");
		weekDayList.add("Mardi");
		weekDayList.add("Mercredi");
		weekDayList.add("Jeudi");
		weekDayList.add("Vendredi");
		weekDayList.add("Samedi");
		
		//On récupère la date d'ajourd'hui
		GregorianCalendar calendar = new GregorianCalendar();
		baseYear = calendar.get(Calendar.YEAR);
		// sur cette methode, le mois est donné entre 0 et 11 donc on ajoute 1
		baseMonth = calendar.get(Calendar.MONTH) + 1;
		baseDay = calendar.get(Calendar.DAY_OF_MONTH);
		// idem ici sauf qu'on veut que la date soit donnée entre 0 et 6 et non 1 et 7
		baseWeekDay = calendar.get(Calendar.DAY_OF_WEEK) -1;
		
		 /* setWeekDay(sc);
		  baseWeekDay = weekDayList.indexOf(weekDay); */
		
		System.out.println("On est le " + weekDayList.get(baseWeekDay) + " " + baseDay + "/" + baseMonth + "/" + baseYear);
		System.out.println("Saisissez le jour de la date que vous chercher");
		day = sc.nextInt();
		System.out.println("Saisissez le mois de la date que vous chercher");
		month = sc.nextInt();
		System.out.println("Saisissez l'année de la date que vous chercher");
		year = sc.nextInt();
		// on cherche si les dates saisis existent
		if(ifDateExist()) {
			System.out.println("La date existe");
			int numbersOfDayOfDifference;
			// on cherche si la date donnée est avant ou après celle d'adj
			if(ifBaseDateisBeforeDate()) {
				// on cherche le nombre de jours de différence entre adj et la date indiquée
				numbersOfDayOfDifference = numbersOfDateOfDifferenceAfter();
				System.out.println(numbersOfDayOfDifference);
				
				result = (numbersOfDayOfDifference+baseWeekDay)%7;
				System.out.println(weekDayList.get(result));
			}else {
				// on cherche le nombre de jours de différence entre adj et la date indiquée
				numbersOfDayOfDifference = numbersOfDateOfDifferenceBefore();
				System.out.println(numbersOfDayOfDifference);
				
				result = (numbersOfDayOfDifference+baseWeekDay)%7;
				if(result<0) result+=7;
				System.out.println(weekDayList.get(result));
			}
		}else {
			System.out.println("La date n'existe pas");
		}
		sc.close();
		System.exit(0);
	}
	
	private boolean ifDateExist() {
		if(month<1 || day<1 || month>12 || day>31 || year<1584) {
			System.out.println("1: La date renseignée n'existe pas");
			return false;
		}else {
			if(day==31) {
				if(!(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)) {
					System.out.println("3: La date renseignée n'existe pas");
					return false;
				}
			}
			if(day==29 && month==2) {
				if(!(year%400==0)) {
					if(!(year%4==0 && !(year%100==0))) {
						System.out.println("5: La date renseignée n'existe pas");
						return false;
					}
				}
			}
			if(year==1584 && month<11) {
				System.out.println("6: La date renseignée n'existe pas");
				return false;
			}
			return true;
		}
	}
	
	private int numbersOfDateOfDifferenceAfter() {
		int var=0;
		boolean finish=false;
		int[] list = new int[3];
		int year=this.baseYear, month=this.baseMonth, day=this.baseDay;
		while(finish==false) {
			if(year>this.year) {
				finish=true;
			}else {
				if(year==this.year) {
					if(month>this.month) {
					finish=true;
					}else {
						if(month==this.month) {
							if(day>=this.day) {
								finish=true;
							}else {
								list = addADay(day, month, year);
								day=list[0];
								month=list[1];
								year=list[2];
								var++;
							}
						}else {
							list = addADay(day, month, year);
							day=list[0];
							month=list[1];
							year=list[2];
							var++;
						}
					}
				}else {
					list = addADay(day, month, year);
					day=list[0];
					month=list[1];
					year=list[2];
					var++;
				}
			}
		}
		
		return var;
	}
	
	
	
	private int[] addADay(int day,int month,int year){
		int[] liste = new int[3];
		if(day==30 || day==31) {
			if(day==31) {
				day=1;
				if(month==12) {
					year++;
					month=1;
				}else {
					month++;
				}
			}else {
				if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) {
					day=31;
				}else {
					day=1;
					month++;
				}
			}
		}else {
			if(month==2) {
				if(day==28) {
					if(year%400==0) {
						day++;
					}else {
						if(year%4==0 && !(year%100==0)) {
							day++;
						}else {
							day=1;
							month=3;
						}
					}
				}else {
					if(day==29) {
						day=1;
						month=3;
					}else {
						day++;
					}
				}
			}else {
				day++;
			}
		}
		liste[0] = day;
		liste[1] = month;
		liste[2] = year;
		return liste;
		
	}
	
	private int numbersOfDateOfDifferenceBefore() {
		int var=0;
		boolean finish=false;
		int[] list = new int[3];
		int year=this.baseYear, month=this.baseMonth, day=this.baseDay;
		while(finish==false) {
			if(year<this.year) {
				finish=true;
			}else {
				if(year==this.year) {
					if(month<this.month) {
					finish=true;
					}else {
						if(month==this.month) {
							if(day<=this.day) {
								finish=true;
							}else {
								list = removeADay(day, month, year);
								day=list[0];
								month=list[1];
								year=list[2];
								var--;
							}
						}else {
							list = removeADay(day, month, year);
							day=list[0];
							month=list[1];
							year=list[2];
							var--;
						}
					}
				}else {
					list = removeADay(day, month, year);
					day=list[0];
					month=list[1];
					year=list[2];
					var--;
				}
			}
		}
		return var;
	}

	
	private int[] removeADay(int day,int month,int year){
		int[] liste = new int[3];
		if(day==1) {
			if(month==1) {
				year--;
				month=12;
				day=31;
			}else {
				if(month-1==2) {
					if(year%400==0) {
						day=29;
						month=2;
					}else {
						if(year%4==0 && !(year%100==0)) {
							day=29;
							month=2;
						}else {
							day=28;
							month=2;
						}
					}
				}else {
					if(month-1==1 || month-1==3 || month-1==5 || month-1==7 || month-1==8 || month-1==10) {
						day=31;
						month--;
					}else {
						day=30;
						month--;
					}
				}	
			}
			
			
		}else {
			day--;
		}
		liste[0] = day;
		liste[1] = month;
		liste[2] = year;
		return liste;
		
	}
	
	private boolean ifBaseDateisBeforeDate() {
		if(baseYear<year) return true;
		else if(baseYear==year) {
			if(baseMonth<month) return true;
			else if(baseMonth==month) {
				if(baseDay<day) return true;
				else return false;
			}else return false;
		}else return false;
	}
	
}
