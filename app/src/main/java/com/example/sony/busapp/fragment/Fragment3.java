package com.example.sony.busapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sony.busapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fragment3 extends Fragment {

    public Fragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contact_form, container, false);
        final EditText your_name = (EditText) rootView.findViewById(R.id.your_name);
        final EditText your_email = (EditText) rootView.findViewById(R.id.your_email);
        final EditText your_subject = (EditText) rootView.findViewById(R.id.your_subject);
        final EditText your_message = (EditText) rootView.findViewById(R.id.your_message);

        Button email = (Button) rootView.findViewById(R.id.post_message);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = your_name.getText().toString();
                String email = your_email.getText().toString();
                String subject = your_subject.getText().toString();
                String message = your_message.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    your_name.setError("Enter Your Name");
                    your_name.requestFocus();
                    return;
                }

                Boolean onError = false;
                if (!isValidEmail(email)) {
                    onError = true;
                    your_email.setError("Invalid Email");
                    return;
                }

                if (TextUtils.isEmpty(subject)) {
                    your_subject.setError("Enter Your Subject");
                    your_subject.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(message)) {
                    your_message.setError("Enter Your Message");
                    your_message.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);

            /* Fill it with Data */
                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"dohuuthinh273@gmail.com"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                        "name:" + name + '\n' + "Email ID:" + email + '\n' + "Message:" + '\n' + message);

            /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(sendEmail, "Send mail..."));


            }
        });
        return rootView;
    }

    // validating email id

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}

