package in.gov.hartrans.etickets.Models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by manojK. on 10-May-2018.
 */

public class PrefManager {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }
    public void saveLoginDetails(String pName, String pEmail, String pPhone, String pIDC) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pName", pName);
        editor.putString("pEmail", pEmail);
        editor.putString("pPhone", pPhone);
        editor.putString("pIDC", pIDC);
        editor.commit();
    }

    public String getpName() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pName", "");
    }
    public String getpEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pEmail", "");
    }
    public String getpPhone() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pPhone", "");
    }
    public String getpIDC() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pIDC", "");
    }
    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("customerDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("pEmail", "").isEmpty();
        boolean isNameEmpty = sharedPreferences.getString("pPhone", "").isEmpty();
        return isEmailEmpty || isNameEmpty;
    }
}
