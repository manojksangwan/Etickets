package in.gov.hartrans.etickets.Models;

import com.android.volley.VolleyError;
import java.util.ArrayList;

/**
 * Created by manojK on 05/12/2016.
 */
public interface eTicketInfoUpdate_iResult {
    void notify_eTicketInfoUpdate_Error(VolleyError error);
    void notify_eTicketInfoUpdate_Success(boolean DidError, String ErrorMessage);
}
