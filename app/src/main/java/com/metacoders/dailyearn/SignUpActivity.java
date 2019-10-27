package com.metacoders.dailyearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.models.modelForBalDb;
import com.metacoders.dailyearn.models.modelForProfile;
import com.metacoders.dailyearn.models.modelForaddDb;
import com.metacoders.dailyearn.models.modelForafflitaion;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class SignUpActivity extends AppCompatActivity implements Validator.ValidationListener {
    CountryCodePicker ccp;
    String  countryCode  ;
    TextInputEditText fnameinput , lnameinput ,  affliationinput
           , usernameinput , dobpicker ;

    @NotEmpty
   @Email
    TextInputEditText emailinput  ;
    @ConfirmPassword
    TextInputEditText confirmpassinput ;
    @Password(min = 6)
    TextInputEditText passinput  ;

    TextInputEditText mobileinput ;
    String  name  , genCount=  "1" , owngenCount = "1" ,  email  , headLead = "null" , pass ,confirmpass , affliation , mobilenum , dateOfBirth , countryName  , username  , affliatedOf  = "null"   ;
    Button signUp ;
    int bonus ;
int gen ;
    FirebaseAuth mAuth ;
    String newPath ;
     String aff  = "null";
    final ArrayList<modelForafflitaion> TownList = new ArrayList<>();
    final ArrayList<String> TownNameList = new ArrayList<>();
boolean isaff  = false  ;
int y ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

      //init views
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        fnameinput = findViewById(R.id.fname) ;
        lnameinput = findViewById(R.id.lname) ;
        emailinput = findViewById(R.id.emailid) ;
        passinput = findViewById(R.id.password) ;
        confirmpassinput = findViewById(R.id.confirmPassword) ;
        affliationinput = findViewById(R.id.affliationID) ;
        mobileinput = findViewById(R.id.phoneNumber) ;
        signUp = findViewById(R.id.signupBtn);
        usernameinput = findViewById(R.id.userName) ;
        Button signout = findViewById(R.id.signout);

    final Validator   validator = new Validator(this);
        validator.setValidationListener(SignUpActivity.this);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth ma = FirebaseAuth.getInstance();
               ma.signOut();
               showToast("SIGNOUT ");
            }
        });
      // countryCode = ccp.getSelectedCountryCodeWithPlus() ;

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                validator.validate();





            }
        });

        // calling methods


    }


    private  void signupingoogle(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, upload the profile data to firebase




                            uploadProfileDataToFirebase() ;





                        } else {
                            // If sign in fails, display a message to the user.

                            showToast("Authentication failed.");
                        }

                        // ...
                    }
                });


    }

    private void registerAsAffliation() {

        DatabaseReference  afffRef  = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(affliatedOf);

        afffRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modelForProfile model = dataSnapshot.getValue(modelForProfile.class);

                String affLink = model.getHeadLead() ;
                String  g = model.getOwngencount() ;

                 gen  = Integer.parseInt(g) ;

                gen = gen + 1  ;
                headLead = affLink +  "+" + getmyUid()  ;




                // now upload the data

                 DatabaseReference mp = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(getmyUid());

                 mp.child("owngencount").setValue(String.valueOf(gen)) ;
                 mp.child("headLead").setValue(headLead).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {

                        genrateGroupOfMine();


                     }
                 }) ;



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });















    }


    private void uploadProfileDataToFirebase() {



        String delegate = "hh:mm aaa";
        String  Time = String.valueOf(DateFormat.format(delegate, Calendar.getInstance().getTime()));
        String   DATE = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        DATE = DATE + " "+Time;


        final String uid = mAuth.getUid() ;

        DatabaseReference profileDb = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(uid);

        // trying to upload to data base with the help of hash map
        if( affliatedOf.contains("null") )
        {
            affliatedOf = FirebaseAuth.getInstance().getUid()  ;


        }




        HashMap datamap = new HashMap();

        datamap.put("name" , name);
        datamap.put("password" , pass);
        datamap.put("mail" , email);
        datamap.put("affiliationOf" , affliatedOf);
        datamap.put("dob" , dateOfBirth);
        datamap.put("country" , countryName);
        datamap.put("phone" , mobilenum);
        datamap.put("username" , username);
        datamap.put("nid_passport" ,"null");
        datamap.put("uid", uid) ;
        datamap.put("genCount" , genCount);
        datamap.put("status" ,"inactive");
         aff = createAfliationCode(mobilenum , uid , username);
        datamap.put("my_AffiliationId" , aff) ;
        datamap.put("joining_Date" , DATE) ;
        datamap.put("headLead"  , uid ) ;
        datamap.put("owngencount" , "1") ;



        profileDb.setValue(datamap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        DatabaseReference profileDb = FirebaseDatabase.getInstance().getReference("userNameDb").child(username);
                        profileDb.child("uid").setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                DatabaseReference affdb = FirebaseDatabase.getInstance().getReference("affdblist").child(aff);
                                affdb.child("uid").setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        balnaceCreation();

                                        if(isaff)
                                        {
                                            showToast("working with affliation");
                                            registerAsAffliation() ;

                                        }

                                        else gotonext();

                                      //  genrateGroupOfMine();
                                    }
                                }) ;


                            }
                        }) ;



                    }
                });









    }



    private  void balnaceCreation( ){

        String uid = mAuth.getUid() ;

        DatabaseReference balDb = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(uid).child(constants.baldb);


        HashMap balmap = new HashMap();

        balmap.put("joining_Bonus" , "1") ;
        balmap.put("afflicted_Bonus" , "0") ;
        balmap.put("mutual_Bonus" , "0") ;
        balmap.put("earn_Bonus" , "0") ;
        balmap.put("global_profit_share" , "0") ;
        balmap.put("reward_Bonus" , "0") ;

        balDb.setValue(balmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {



            }
        }) ;


    }


    private  void checkingusername() {
        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("userNameDb").child(username);

        // checking id the username exist or not
        userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {


                    showToast("UserName Taken");


                }
                else {

                    if(!affliation.isEmpty())
                    {

                        checkingaffliationID();
                    }
                    else
                    {
                        // straight  signup users with gmail
                        signupingoogle();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private  void checkingaffliationID() {
        DatabaseReference  affid = FirebaseDatabase.getInstance().getReference("affdblist").child(affliation);
        affid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    isaff = true ;

                    modelForaddDb modell =  dataSnapshot.getValue(modelForaddDb.class) ;
                    affliatedOf = modell.getUid() ;
                    signupingoogle();

                }
                else {

                    showToast("Wrong Affliation ID");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




    private  void getDataFromViews() {
        name = fnameinput.getText().toString() + " " + lnameinput.getText().toString() ;
        email = emailinput.getText().toString() ;
        affliation = affliationinput.getText().toString();
        pass = passinput.getText().toString();
        confirmpass = confirmpassinput.getText().toString() ;
        mobilenum = ccp.getSelectedCountryCodeWithPlus() + mobileinput.getText().toString() ;
        countryName = ccp.getSelectedCountryName() ;
        username = usernameinput.getText().toString();


    }
    private  void showToast(String s) {
        Toast.makeText(SignUpActivity.this , s , Toast.LENGTH_SHORT)
                .show();

    }


    private void giveBonusTothehigherGen() {

        // now the hard work
        /* we have the list of  the use */

        DatabaseReference mref  = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(affliatedOf);

        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                modelForProfile  mode = dataSnapshot.getValue(modelForProfile.class) ;

              //  headLead = mode.getHeadLead()  ;
                int genlv = Integer.parseInt(mode.getOwngencount() );

                spiltheadleadintouids(headLead, gen) ;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //




        /*


        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("affdblist").child(affliation).child("lists");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TownList.clear();
                TownNameList.clear();

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    modelForafflitaion departments = deptSnapshot.getValue(modelForafflitaion.class);
                    //adding department to the list
                    TownList.add(departments);
                }

                if (TownList.size() > 0) {
                    for (int i = 0; i < TownList.size(); i++) {


                        TownNameList.add(TownList.get(i).getUid());
                        TownNameList.add(TownList.get(i).getGenLvl()) ;

                        showToast(TownList.get(i).getUid());

                        if(!TownList.get(i).getUid().equals(getmyUid()))
                        {
                            showToast(TownList.get(i).getUid());
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(constants.profileLink)
                                    .child(TownList.get(i).getUid()).child(constants.baldb).child("earn_Bonus");
                                databaseReference.setValue("yes") ;


                        }




                    }
                }




                //  showToast("done");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/



    }

    private void spiltheadleadintouids(String headlead , int genlvl)
    {

       // showToast(headlead);

        Vector< String > vector = new Vector<>();                  // Storage

        String str = headlead ;

        StringBuilder temp = new StringBuilder();               // To temporarily store our desired string.

        for(Object i : str.toCharArray()){                      // Yeah, we need Object class since we don't have auto or autoptr in Java to run a foreach loop. Yare yare-daze.
            if(Character.isLetterOrDigit((Character) i))               // This method returns a boolean value of parameter. True if the parameter is alphanumeric.
                temp.append(i.toString());                      // Concatenation of a string just like C++14
            else{
                if(!temp.toString().equals(""))                 // We don't want blank string on our vector, do we?
                    vector.add(temp.toString());
                temp = new StringBuilder();                     // Allocates a new memory location for the StringBuilder, basically emptying the StringBuilder.
            }
        }

        if(!temp.toString().isEmpty())                          // A special case is when no symbols or numbers are given input. In that case add the original string.
            vector.add(temp.toString());
         y = genlvl -1  ;
        // showToast("gentes" + y);
       //System.out.println("Case " + tst.toString() + ":");
        for(final Object i : vector)
        {

                if(i.toString().equals(getmyUid()))
                {
                    // add bonus level accorudlingly

                }
                else

                    {
                    wrteBonus(i.toString() , y ) ;

                }

       //    System.out.println("vector["+ y +  "] -> " + i.toString() + "");

            y-- ;
        }


        Handler  handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                gotonext();

            }
        } , 1500) ;

    }

    private void wrteBonus(final String s, final int Y) {

        /// read the data from the server
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(constants.profileLink)
                .child(s).child(constants.baldb);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelForBalDb   model = dataSnapshot.getValue(modelForBalDb.class) ;


                bonus =Integer.parseInt(model.getEarn_Bonus());
            showToast(Y+ "y");

                if(Y == 3)
                {

                    bonus = bonus+ 1;

                }
                else if ( Y == 2)
                    bonus = bonus+ 2 ;
                else if ( Y == 1)
                    bonus = bonus+ 3 ;

                else if ( Y == 0)
                    bonus = bonus+ 4554454;

                databaseReference.child("earn_Bonus").setValue(bonus+ "").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                       // showToast(s);
                       // databaseReference.child("reward_Bonus").setValue("ajnsdkjf") ;

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private  String  createAfliationCode(String phn  , String uid , String name ) {
        String delegate = "hh";
        String  Time = String.valueOf(DateFormat.format(delegate, Calendar.getInstance().getTime()));

        return name+Time  ;

    }


    private  void genrateGroupOfMine() {


        DatabaseReference mref  = FirebaseDatabase.getInstance().getReference("affdblist").child(affliation);
        mref.child("list").child(getmyUid()).child("uid").setValue(getmyUid())
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                 //   balnaceCreation();

                    giveBonusTothehigherGen();

            }
        })        ;




    }


    private  String   getmyUid()
    {
        String i  = FirebaseAuth.getInstance().getUid() ;


        return  i ;
    }



    private  void gotonext()
    {
        Intent o = new Intent(getApplicationContext()  , DailyActivity.class);

        startActivity(o);
        finish();


    }


    @Override
    public void onValidationSucceeded() {
        getDataFromViews() ;
        checkingusername();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof TextInputLayout) {
                ((TextInputLayout) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
