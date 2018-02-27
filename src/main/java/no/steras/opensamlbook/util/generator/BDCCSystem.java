package no.steras.opensamlbook.util.generator;


import com.tone.constant.ConfigFactory;

import java.util.Calendar;


public class BDCCSystem {

    public static final String CONFIG_SECTION_APPLICATION = "application";
    public static final String CONFIG_SECTION_ENTRY_APPID = "appid";
    public static final String CONFIG_SECTION_ENTRY_MACHINEID = "machineid";
    public static final String CONFIG_SECTION_ENTRY_BUSINESS_DATE_OFF_SET = "system.business_date_off_set";
    public static final String CONFIG_SECTION_ENTRY_BATCH_BUSINESS_DATE_SWITCH_HOUR = "system.batch_business_date_switch_hour";
    public static final String CONFIG_SECTION_ENTRY_RECORD_STATUS = "system.record_status";

    public static final String SYSTEM_RECORD_STATUS_ACTIVE = "A";
    public static final String SYSTEM_RECORD_STATUS_INACTIVE = "I";

    public static final Long SYSTEM_LONG_ZERO = new Long(0);

    public static final int SWITCH_END_HOUR = 24;
    public static final int TIME_MILLS_IN_ONE_DAY = 1000 * 60 * 60 * 24;
    public static final String CONFIG_SECTION_PLATFORM = "platformdata";
    public static final String CONFIG_SECTION_PLATFORM_INDEX = "indexconfig";


    /**
     * Get BDCC business date for online function.
     *
     */
    public static long currentOnlineTimeMillis()
    {
        long currentTimeMills = System.currentTimeMillis();
        String offSet = getParameter(CONFIG_SECTION_APPLICATION, CONFIG_SECTION_ENTRY_BUSINESS_DATE_OFF_SET);
        if(offSet != null)
        {
            long offSetTimeMills = Long.parseLong(offSet) * TIME_MILLS_IN_ONE_DAY;
            currentTimeMills += offSetTimeMills;
        }
        return currentTimeMills;
    }

    /**
     * Get BDCC business date for online function.
     *
     */
    public static java.util.Date currentOnlineTime()
    {

        return new java.util.Date(currentOnlineTimeMillis());
    }

    /**
     * Get BDCC business date for batch function.
     *
     */
    public static long currentBatchTimeMillis()
    {
        long currentTimeMills = System.currentTimeMillis();
        String switchHour = getParameter(CONFIG_SECTION_APPLICATION, CONFIG_SECTION_ENTRY_BATCH_BUSINESS_DATE_SWITCH_HOUR);
        String offSet = getParameter(CONFIG_SECTION_APPLICATION, CONFIG_SECTION_ENTRY_BUSINESS_DATE_OFF_SET);
        if(offSet != null && switchHour != null)
        {
            long offSetTimeMills = Long.parseLong(offSet) * TIME_MILLS_IN_ONE_DAY;
            currentTimeMills += offSetTimeMills;
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
            int currentSecond = Calendar.getInstance().get(Calendar.SECOND);
            if(currentHour < Integer.parseInt(switchHour))
            {
                currentTimeMills -= (currentHour*60*60 + currentMinute*60 + currentSecond + 1)*1000;
            }
        }
        return currentTimeMills;
    }

    /**
     * Get the system config for the application id and machine id.
     * @param key
     * @return
     */
    public static String getParameter(String section, String key)
    {
        return ConfigFactory.getInstance().getConfig().getString(section, key);
    }

    /**
     * Get the system config for the application id and machine id.
     * @param key
     * @return
     */
    public static long getLongParameter(String section, String key)
    {
        String value = ConfigFactory.getInstance().getConfig().getString(section, key);
        try{
            return Long.valueOf(value).longValue();
        }catch(Exception e){
            return 1;
        }
    }




}
