package kr.hs.emirim.w2027.ex14_10_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    EditText editCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editCall = findViewById(R.id.edit_call);
        Button btnCall = findViewById(R.id.btn);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, MODE_PRIVATE);
        btnCall.setOnClickListener(btnListener);
    }
    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            editCall.setText(getCallHistory());
        }
    };
    public String getCallHistory(){
        String[] callSet = {CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, callSet, null, null, null);
        if (cursor==null)
            return "통화기록이 전혀 없음";
        StringBuffer callBuff = new StringBuffer();
        callBuff.append("\n날짜 : 구분 : 전화번호 : 통화시간 \n\n");
        cursor.moveToFirst();
        do {
            long callDate = cursor.getLong(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(new Double(callDate));
            callBuff.append(strDate + ":"); //날짜값 누적
            if(cursor.getInt(1)==CallLog.Calls.INCOMING_TYPE){
                callBuff.append("착신 : ");
            }else{
                callBuff.append("발신 : ");
            }
            callBuff.append(cursor.getString(2)+":");
            callBuff.append(cursor.getString(3)+"초\n");
        }while (cursor.moveToNext()); //읽을 수 있을 동안 True반복해서 실행함
        cursor.close();
        return callBuff.toString();
    }
}