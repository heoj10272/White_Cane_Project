package kr.ac.skuniv.for_impairment_project.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import kr.ac.skuniv.for_impairment_project.R;

public class ProtectorPastMoveActivity extends AppCompatActivity {

    private TextView PastDate;
    private Button PastDateButton;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.protector_pastmove);

        PastDate = (TextView) findViewById(R.id.PastDate);
        PastDateButton= (Button) findViewById(R.id.PastDateButton);

        this.InitializeView();
        this.InitializeListener();
        PastDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickHandler(view);
            }
        });

    }

    public void InitializeView()
    {
        PastDate = (TextView)findViewById(R.id.PastDate);
    }

    public void InitializeListener()
    {
        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                PastDate.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler(View view)
    {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2022, 5, 20);

        dialog.show();
    }

}