package configuration;

import util.MainInitData;



public class LoginlInitData extends MainInitData
{
    /**
     * Script Name   : <b>LoginlInitData</b>
     * Generated     : <b>September 23, 2012</b>
     * Description   : Functional Test Script
     *
     * @since  2012/09/25
     * @author Abu
     */
    protected static LoginlInitData loginData = null;


    public static String login_password ="";
    public static String app_server="";
    public static String app_title="";
    public static String contact_us="";
    public static String contact_ustext="";
    public static String logout="";

    public static String jtrillogin_password ="";
    public static String jtrilapp_server="";
    public static String valapp_title="";
    public static String valppc_optimization="";
    public static String valadd_campaign_google="";
    public static String valedit_campaign_google="";
    public static String valadd_campaign_bing="";
    public static String valedit_campaign_bing="";
    public static String valadd_adgroup_google="";
    public static String valedit_adgroup_google="";
    public static String valadd_exactkeyword_google="";
    public static String valadd_phrasekeyword_google="";
    public static String valadd_broadkeyword_google="";

    public static String valedit_exactkeyword_google="";
    public static String valedit_phrasekeyword_google="";
    public static String valedit_broadkeyword_google="";

    public static String valadd_textads_google="";
    public static String valadd_mobile_google="";
    public static String valadd_mobcarriers_google="";
    public static String valadd_mob_markuplang_google="";
    public static String valadd_negkeyword_google="";
    public static String valadd_placement_google="";
    public static String valadd_mobile_image_google="";


    /**
     * Constructor to initialize location variables.
     *
     * @throws Exception
     */
    protected LoginlInitData() throws Exception
    {
        super();
        // initialize variables
        initLoginlVariables();
    }
    //single instance of LocationData object
    public static LoginlInitData getInstance() throws Exception
    {
        if(loginData == null)
            loginData = new LoginlInitData();

        return loginData;
    }

    public void initLoginlVariables () throws Exception
    {
        // Valencia test data load
        jtrilapp_server = getScriptValue("jtrilapp_server");
        jtrillogin_password = getScriptValue("jtrillogin_password");
        valapp_title = getScriptValue("valenciaapp_title");
        valppc_optimization = getScriptValue("ppc_optimization");

        // Load Google Campaign related all test data into array
        valadd_campaign_google = getScriptValue("add_Campaign_google");
        valedit_campaign_google = getScriptValue("edit_Campaign_google");
        valadd_adgroup_google = getScriptValue("add_AdGroup_google");
        valedit_adgroup_google = getScriptValue("edit_AdGroup_google");

        valadd_exactkeyword_google = getScriptValue("add_ExactKeyword_google");
        valadd_phrasekeyword_google = getScriptValue("add_PhraseKeyword_google");
        valadd_broadkeyword_google = getScriptValue("add_BroadKeyword_google");

        valedit_exactkeyword_google = getScriptValue("edit_ExactKeyword_google");
        valedit_phrasekeyword_google = getScriptValue("edit_PhraseKeyword_google");
        valedit_broadkeyword_google = getScriptValue("edit_BroadKeyword_google");

        valadd_textads_google = getScriptValue("add_TextAds_google");
        valadd_mobile_google = getScriptValue("add_Mobile_google");
        valadd_mobile_image_google = getScriptValue("add_Mobile_Image_google");
        valadd_mobcarriers_google = getScriptValue("add_Mobile_Carriers_google");
        valadd_mob_markuplang_google = getScriptValue("add_Mobile_Markup_Languages_google");
        valadd_negkeyword_google = getScriptValue("add_NegativeKeywords_google");
        valadd_placement_google = getScriptValue("add_Placement_google");

        // Load Bing Campaign related all test data into array
        valadd_campaign_bing = getScriptValue("add_campaign_bing");
        valedit_campaign_bing = getScriptValue("edit_campaign_bing");

    }
}

