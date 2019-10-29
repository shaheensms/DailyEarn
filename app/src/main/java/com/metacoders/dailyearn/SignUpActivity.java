package com.metacoders.dailyearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.dailyearn.models.modelForProfile;
import com.metacoders.dailyearn.models.modelForaddDb;
import com.metacoders.dailyearn.models.modelForafflitaion;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    CountryCodePicker ccp;
    String  countryCode  ;
    TextInputEditText fnameinput , lnameinput , emailinput , passinput , confirmpassinput , affliationinput  , mobileinput
           , usernameinput , dobpicker ;
    String  name , genCount=  "1" , owngenCount = "1" ,  email  , headLead = "null" , pass ,confirmpass , affliation , mobilenum , dateOfBirth , countryName  , username  , affliatedOf  = "null"   ;
    Button signUp ;
    FirebaseAuth mAuth ;
    String newPath ;
     String aff  = "null";
boolean isaff  = false  ;



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
                getDataFromViews() ;
                checkingusername();




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

                            if(isaff){
                                registeraffliation(affliatedOf , genCount , headLead )  ;
                            }



                        } else {
                            // If sign in fails, display a message to the user.

                            showToast("Authentication failed.");
                        }

                        // ...
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
        datamap.put("headLead"  , "null" ) ;
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

                                        genrateGroupOfMine();
                                    }
                                }) ;






                            }
                        }) ;



                    }
                });









    }

    private  void createlvl() {
        String uid = mAuth.getUid() ;

        DatabaseReference profileDb = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(uid).child(constants.gen);

        HashMap genmap = new HashMap();

        genmap.put("lvl1" , "null");
        genmap.put("lvl2" , "null");
        genmap.put("lvl3" , "null");
        genmap.put("lvl4" , "null");
        genmap.put("lvl5" , "null");
        genmap.put("lvl6" , "null");
        genmap.put("lvl7" , "null");
        genmap.put("lvl8" , "null");
        genmap.put("lvl9" , "null");
        genmap.put("lvl10" , "null");
        genmap.put("lvl11" , "null");
        genmap.put("lvl12" , "null");
        genmap.put("lvl13" , "null");
        genmap.put("lvl14" , "null");
        genmap.put("lvl15" , "null");

        profileDb.setValue(genmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                // proceed with the balance generation

                balnaceCreation();

            }
        }) ;




    }
    private  void balnaceCreation( ){

        String uid = mAuth.getUid() ;

        DatabaseReference balDb = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(uid).child(constants.baldb);


        HashMap balmap = new HashMap();

        balmap.put("joining_Bonus" , "null") ;
        balmap.put("afflicted_Bonus" , "null") ;
        balmap.put("mutual_Bonus" , "null") ;
        balmap.put("earn_Bonus" , "null") ;
        balmap.put("global_profit_share" , "null") ;
        balmap.put("reward_Bonus" , "null") ;

        balDb.setValue(balmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                giveBonusTothehigherGen();

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




                    DatabaseReference  rr = FirebaseDatabase.getInstance().getReference("affliationGroup").child(affliatedOf);

                    rr.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            modelForafflitaion model =  dataSnapshot.getValue(modelForafflitaion.class) ;


                            genCount = model.getGenCount() ;
                            headLead = model.getHead();



                                signupingoogle();









                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




            //        signupingoogle();

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

    private void registeraffliation(final String affliatedOf, final String genCountt, String HeadLeaad) {


      final  String uidds = FirebaseAuth.getInstance().getUid() ;
        String lv = "lvl"+genCountt  ;
        DatabaseReference create = FirebaseDatabase.getInstance().getReference("affliationGroup").child(affliatedOf).child(uidds);

        modelForafflitaion modelForafflitaion =new modelForafflitaion(uidds , genCountt , HeadLeaad , lv) ;

        create.setValue(modelForafflitaion) ;

        DatabaseReference createaffdb = FirebaseDatabase.getInstance().getReference("affdblist").child(affliation).child("lists").child(uidds);


        modelForafflitaion modelForlist =new modelForafflitaion(uidds , genCountt , HeadLeaad , lv) ;

        createaffdb.setValue(modelForlist) ;



        int newgen  =  Integer.parseInt(genCount)+1 ;
        DatabaseReference update = FirebaseDatabase.getInstance().getReference("affliationGroup").child(affliatedOf).child("genCount");
        genCount = newgen + "";
        update.setValue(String.valueOf(newgen)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

              //  signupingoogle();





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
        Toast.makeText(SignUpActivity.this , s , Toast.LENGTH_LONG)
                .show();

    }


    private void giveBonusTothehigherGen() {

        // now the hard work
        /* we have the list of  the use */

        //  DatabaseReference mref  = FirebaseDatabase.getInstance().getReference("affdblist").child(aff);
        final ArrayList<modelForafflitaion> TownList = new ArrayList<>();
        final ArrayList<String> TownNameList = new ArrayList<>();

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


    }

            private  String  createAfliationCode(String phn  , String uid , String name ) {
        String delegate = "hh";
        String  Time = String.valueOf(DateFormat.format(delegate, Calendar.getInstance().getTime()));

        return name+Time  ;

    }


    private  void genrateGroupOfMine()
    {


        final String UID = FirebaseAuth.getInstance().getUid() ;

         DatabaseReference gengrop = FirebaseDatabase.getInstance().getReference("affliationGroup").child(UID);


        HashMap genmap = new HashMap();


        genmap.put("head" ,  headLead ) ;
        genmap.put("genCount" ,"1");
        genmap.put("uid" , FirebaseAuth.getInstance().getUid()) ;

        /*
        genmap.put("lvl1" , "null");
        genmap.put("lvl2" , "null");
        genmap.put("lvl3" , "null");
        genmap.put("lvl4" , "null");
        genmap.put("lvl5" , "null");
        genmap.put("lvl6" , "null");
        genmap.put("lvl7" , "null");
        genmap.put("lvl8" , "null");
        genmap.put("lvl9" , "null");
        genmap.put("lvl10" , "null");
        genmap.put("lvl11" , "null");
        genmap.put("lvl12" , "null");
        genmap.put("lvl13" , "null");
        genmap.put("lvl14" , "null");
        genmap.put("lvl15" , "null");
*/

        gengrop.setValue(genmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                     //   DatabaseReference  updateProfile = FirebaseDatabase.getInstance().getReference(constants.profileLink).child(UID).child("");

                        balnaceCreation();
                    }
                }) ;





    }


    private  String   getmyUid()
    {
        String i  = FirebaseAuth.getInstance().getUid() ;


        return  i ;
    }






}
