# seleniumAutomation
It is a usecase automation in selenium webdriver

Objective
The main goal is to automate customer use cases to reduce manual testing. Automation testing is a Software testing technique to test and compare the actual outcome with the expected outcome. This can be achieved by writing test scripts. Test automation is used to automate repetitive tasks and other testing tasks which are difficult to perform manually.

Installation/Setup
Setup for Selenium 

Download & Install the Java Software Development Kit (JDK)

Download “Eclipse IDE for Java Developers”. Be sure to choose correctly between Windows 32 bit and 64 bit versions

Selenium Java Client Driver

Chrome Driver - “webdriver.chrome.driver” Chrome Driver is a separate executable that WebDriver uses to control Chrome. This executable starts a server on local system to run the Selenium WebDriver Test Scripts.

Setup for TestNG

Launch Eclipse, On Menu bar click help → Eclipse Marketplace..

Search TestNG in given below Text field.

Click on install button

Once it get installed, restart the eclipse

Setup for maven project

File →  New → Project → Click  new maven folder

Adding TestNG dependency 

Google → maven repository → Search testNG and click enter

TestNG → see version  which people used maximum  (currently)→ 6.14.3(IT may vary)

Copy dependencies and paste in POM.xml .

Steps to set JRE setting system library to 1.8

Right click on project →  Build path → Configure build path

Check JRE is set to 1.8 

Steps to run project in NodeJS

Download NodeJs : https://nodejs.dev/.

Open terminal in VSCode. Run npm i. These will install node module dependencies.

Run npm i -g nodemon.

Run nodemon.

Tools Used
Eclipse

Selenium Webdriver

TestNG

Maven

Extents reports

NodeJS

 

Men's Wear House Automation Process
Module 1
 Step 1: Load “https://tst5.menswearhouse.com/” in browser 

Step 2:

        Reload home page 5 times

        on 5th click user will get offer on home page which is entry page

Step 3:

Check local storage variable : user should get in one of the following category.

      “z1_campaign” includes Test(20of125, 10of75, 15of100).

      “z1_campaign” includes Control


Module 2

If user is in Test group ,user should see repeated banner on following pages in sequence.

 Check repeated offer on Search page

 Check repeated offer on Catalog page

 Check repeated offer on Product page

 Select  size/color → click added to bag → Click on view cart

Check repeated offer on Cart page

Check repeated offer on Home page


Flow Chart


![image](https://github.com/sushmita08/seleniumAutomation/assets/55182715/ff8d1af8-7742-4460-a036-6c0e944b46a5)

Flowchart Description 
On HTML page 

Select Client = TBI/MWH

Select Channel = Desktop

Select Use Cases = RTO

Click RUN button 

Note: 
Kindly do not close OR reload the browser until the execution is complete, else automation execution will be terminate. This Use Case is active for Desktop Channel Only.

UI Flow

![image](https://github.com/sushmita08/seleniumAutomation/assets/55182715/ed5a801f-d098-438d-9850-5ea1d5a52138)

After Clicking RUN button alert box will pop-up

![image](https://github.com/sushmita08/seleniumAutomation/assets/55182715/506bc6c8-575f-4bb1-a10c-7e8f2f481745)

![image](https://github.com/sushmita08/seleniumAutomation/assets/55182715/08f312eb-e2dc-43ac-aa4d-2c05a2e66456)

On alert box click OK to proceed and wait for Test to execute.

After automation is complete you will again get one alert box ,just click OK 

![image](https://github.com/sushmita08/seleniumAutomation/assets/55182715/6de9b48b-b14b-4d56-b429-aedeeed8eee2)

After clicking OK button ,Results button will appear ,Click on RESULT button to see results

![image](https://github.com/sushmita08/seleniumAutomation/assets/55182715/1d66b9ce-f1ed-4183-ad95-074881728ac6)

 Test Results

Tester can find Results of full automation process, reports are graphically designed to show passed/failed test cases.

![image](https://github.com/sushmita08/seleniumAutomation/assets/55182715/bda1c83e-c856-4253-baf9-2f35e96ad6ab)






