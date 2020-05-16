import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.DatePicker
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*


internal class setDate(private val editText: EditText, private val ctx: Context?) :
    OnFocusChangeListener,
    OnDateSetListener {
    private val myCalendar: Calendar
    override fun onDateSet(
        view: DatePicker,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) {
        // this.editText.setText();
        val myFormat = "MMM dd, yyyy" //In which you need put here
        val sdformat = SimpleDateFormat(myFormat, Locale.US)
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        editText.setText(sdformat.format(myCalendar.time))
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            if (ctx != null) {
                DatePickerDialog(
                    ctx, this, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    init {
        editText.onFocusChangeListener = this
        myCalendar = Calendar.getInstance()
    }
}