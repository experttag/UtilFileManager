/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.experttag.util.scheduler;

import com.experttag.util.communication.mail.MailHelper;
import com.experttag.util.constant.DataConstant;
import java.text.SimpleDateFormat;

import java.util.Date;

public class AlarmClock {

    private final Scheduler scheduler = new Scheduler();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS");
    private final int hourOfDay, minute, second;

    public AlarmClock(int hourOfDay, int minute, int second) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
    }

    public void start() {
        scheduler.schedule(new SchedulerTask() {
            public void run() {
                testAlarm();

            }
            private void testAlarm() {
                System.out.println("Wake up! " +  "It's " + dateFormat.format(new Date()));
                MailHelper.sendMail("solara@gmail.com", "testing mailer and scheduler","thanks");

            }
        }, new DailyIterator(hourOfDay, minute, second));
    }

    

    //this scheduler sends PNR status to all persons at predefined time daily
    public static void sendPNRStatus() {
        //AlarmClock alarmClock = new AlarmClock(DataConstant.schedulerHour, DataConstant.schedulerMinutes , DataConstant.schedulerSeconds);

       // alarmClock.start();
    }

    
    public static void main(String args[]){
        AlarmClock alarmClock = new AlarmClock(12, 54 , 0);
        alarmClock.start();
    }

}
