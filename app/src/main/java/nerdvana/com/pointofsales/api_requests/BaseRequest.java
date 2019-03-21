package nerdvana.com.pointofsales.api_requests;

import nerdvana.com.pointofsales.ApplicationConstants;
import nerdvana.com.pointofsales.SharedPreferenceManager;

public class BaseRequest {
    protected String machineNumber = SharedPreferenceManager.getString(null, ApplicationConstants.MACHINE_ID);
    protected String userId = SharedPreferenceManager.getString(null, ApplicationConstants.USER_ID);
    protected String tax = SharedPreferenceManager.getString(null, ApplicationConstants.TAX_RATE);
    protected String currencyId = SharedPreferenceManager.getString(null, ApplicationConstants.COUNTRY_CODE);
    protected String currencyValue = SharedPreferenceManager.getString(null, ApplicationConstants.DEFAULT_CURRENCY_VALUE);
    protected String branchId = SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_ID);
    protected String branchCode = SharedPreferenceManager.getString(null, ApplicationConstants.BRANCH_CODE);

}
