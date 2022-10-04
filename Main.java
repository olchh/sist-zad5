package com.metanit;

public class Main{
    public static void main(String[] args){
        Account account = new Account();
        Popolnit popoln = new Popolnit(account);
        Snimet snim = new Snimet(account);
        new Thread(popoln).start();
        new Thread(snim).start();
    }
}

class Account extends Thread{
    private int balance = 0;
    private final int sum = 1000;

    public synchronized void toPopolnit(){
        while(balance >= sum){
            try{
                wait(); //переводит вызывающий поток в состояние ожидания до тех пор, пока другой поток не вызовет метод notify()
            }
            catch (Exception ex){
            }
        }
        balance += 500;
        System.out.println("пополнение на 500.");
        System.out.println("ваш баланс " + balance);
        notify(); //продолжает работу потока, у которого ранее был вызван метод wait()
    }

    public synchronized void toSnimet(){
        while(balance < sum){
            try {
                wait();
            }
            catch (Exception ex){

            }
        }
        balance -= 1000;
        System.out.println("списано 1000");
        System.out.println("ваш баланс " + balance);
        notify();
    }
}

class Popolnit extends Thread{

    Account account;
    Popolnit(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 10; i++) {
            account.toSnimet();
        }
    }
}
class Snimet extends Thread{

    Account account;
    Snimet(Account account){
        this.account=account;
    }
    public void run(){
        for (int i = 1; i < 10; i++) {
            account.toPopolnit();
        }
    }
}
