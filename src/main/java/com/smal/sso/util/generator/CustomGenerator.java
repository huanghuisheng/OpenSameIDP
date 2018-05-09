package com.smal.sso.util.generator;

import java.io.Serializable;
import java.util.Calendar;
/**
 * <pre>
 * This class generator the sequence number for the DB record. The total is 63 bit:
 * System ID:		10 bit	rang(0 - 1023)
 * Machine ID:		4 bit	rang(0-15)
 * Year:			7 bit	rang(1-99)
 * Month:			4 bit	rang(0-11)
 * Day:				5 bit	rang(1-31)
 * Hour:			5 bit	rang(0-23)
 * Minute:			6 bit	rang(0-59)
 * Second:			6 bit	rang(0-59)
 * Mill-Secound:	10 bit	rang(0-999)
 * Sub Sequence:	6 bit	rang(0-63)
 * </pre>
 */
public class CustomGenerator  {
    private static int subSequence=0;
    private static final int APP_ID_BIT=63-10;
    private static final int MACHINE_ID_BIT=63-10-4;
    private static final int YEAR_BIT=63-10-4-7;
    private static final int MONTH_BIT=63-10-4-7-4;
    private static final int DAY_BIT=63-10-4-7-4-5;
    private static final int HOUR_BIT=63-10-4-7-4-5-5;
    private static final int MINUTER_BIT=63-10-4-7-4-5-5-6;
    private static final int SECOND_BIT=63-10-4-7-4-5-5-6-6;
    private static final int MILSECOND_BIT=63-10-4-7-4-5-5-6-6-10;

    /**
     * Implement the generate interface from Hibernate IdentifierGenerator
     */
    public static Serializable generate(){
        //define variable
        long customID =0;

        Calendar cal = Calendar.getInstance();
        int currYear = cal.get(Calendar.YEAR)%100;
        int currMonth = cal.get(Calendar.MONTH);
        int currDay = cal.get(Calendar.DATE );

        int currHour = cal.get(Calendar.HOUR_OF_DAY);
        int currMinuter = cal.get(Calendar.MINUTE );
        int currSecond = cal.get(Calendar.SECOND );
        int currMilSec = cal.get(Calendar.MILLISECOND);

        //deal with app_code
        customID = customID | LeftShift(BDCCSystem.getLongParameter(BDCCSystem.CONFIG_SECTION_APPLICATION, BDCCSystem.CONFIG_SECTION_ENTRY_APPID),APP_ID_BIT);

        //deal with machine_code
        customID = customID | LeftShift(BDCCSystem.getLongParameter(BDCCSystem.CONFIG_SECTION_APPLICATION, BDCCSystem.CONFIG_SECTION_ENTRY_MACHINEID),MACHINE_ID_BIT);

        //deal with year
        customID = customID | LeftShift(currYear,YEAR_BIT);

        //deal with month
        customID = customID | LeftShift(currMonth,MONTH_BIT);

        //deal with day
        customID = customID | LeftShift(currDay,DAY_BIT);

        //deal with hour
        customID = customID | LeftShift(currHour,HOUR_BIT);

        //deal with minuter
        customID = customID | LeftShift(currMinuter,MINUTER_BIT);

        //deal with Second
        customID = customID | LeftShift(currSecond,SECOND_BIT);

        //deal with millisecond Second
        customID = customID | LeftShift(currMilSec,MILSECOND_BIT);

        //deal with sub sequence
        customID = customID | getSubSequence();

        return new Long(customID);
    }
    /**
     * Implement the generate interface from Hibernate IdentifierGenerator
     */
//    public Serializable generate(SessionImplementor arg0, Object arg1)
//            throws HibernateException {
//        return generate();
//    }

    /**
     * Do the left shift operator
     * @param orginal
     * @param shiftnum
     * @return
     */
    private static long LeftShift(long orginal, int shiftnum){
        return orginal << shiftnum;
    }

    /**
     * Generator the sub sequence, it is synchronized mothed.
     * @return
     */
    private static synchronized int getSubSequence() {
        subSequence++;
        if (subSequence > 63)
        {
            subSequence = 0;
        }
        return subSequence;
    }



}

