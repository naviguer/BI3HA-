public class Login_Opt_Ativity extends AppCompatActivity {
    String CODE;
    private String verificationId;
    private EditText phoneinput;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_opt);

        final ProgressBar progressBar =findViewById(R.id.progressBar);
        button =findViewById(R.id.buttonVerify);
        verificationId =getIntent().getStringExtra("verificationID");
        phoneinput=findViewById(R.id.etOtp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (phoneinput.getText().toString().trim().isEmpty()){
                    Toast.makeText(Login_Opt_Ativity.this, "Please enter valid code ", Toast.LENGTH_SHORT).show();
                    return;
                }
                 CODE = phoneinput.getText().toString();

                if (verificationId!= null){
                    progressBar.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                            verificationId,
                            CODE
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    button.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(getApplicationContext(),Login_Username_Activity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(Login_Opt_Ativity.this, "the verification code entered was invalid ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        findViewById(R.id.resend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+213"+ getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        Login_Opt_Ativity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Login_Opt_Ativity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String NewVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId =NewVerificationId;
                                Toast.makeText(Login_Opt_Ativity.this, "OPT SENT", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }
}
