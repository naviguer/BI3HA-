public class Login_Phone_Opt_Activity extends AppCompatActivity {
    CountryCodePicker login_country_code;
    EditText phoneinput;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_opt);

        phoneinput =findViewById(R.id.phoneinput);
        btn1 =findViewById(R.id.btn1);
        final ProgressBar Progressbar = findViewById(R.id.progressBar);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                if (phoneinput.getText().toString().trim().isEmpty()) {
                    Toast.makeText(Login_Phone_Opt_Activity.this, "Enter mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                Progressbar.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+213"+phoneinput.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        Login_Phone_Opt_Activity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Progressbar.setVisibility(View.GONE);
                                btn1.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Progressbar.setVisibility(View.GONE);
                                btn1.setVisibility(View.VISIBLE);
                                Toast.makeText(Login_Phone_Opt_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Progressbar.setVisibility(View.GONE);
                                btn1.setVisibility(View.VISIBLE);
                                Intent intent=new Intent(getApplicationContext(),Login_Opt_Ativity.class);
                                intent.putExtra("mobile",phoneinput.getText().toString());
                                intent.putExtra("verificationID",verificationId);
                                startActivity(intent);
                            }
                        }
                );
            }
        });
    }
}
