package com.qualcomm.ftcrobotcontroller.opmodes;

import java.lang.Math;
//------------------------------------------------------------------------------
//
// PushBotManual
//
/**
 * Extends the PushBotTelemetry and PushBotHardware classes to provide a basic
 * manual operational mode for the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
// V2 by ALBERT LIU "ARCADE DRIVE"
public class PushBotManualArcade extends PushBotTelemetry

{
    //--------------------------------------------------------------------------
    //
    // PushBotManual
    //
    //--------
    // Constructs the class.
    //
    // The system calls this member when the class is instantiated.
    //--------
    public PushBotManualArcade ()
    float partyTimer = 0;

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotManual::PushBotManual

    //--------------------------------------------------------------------------
    //
    // loop
    //
    //--------
    // Initializes the class.
    //
    // The system calls this member repeatedly while the OpMode is running.
    //--------
    @Override public void loop ()

    {
        //----------------------------------------------------------------------
        //
        // DC Motors
        //
        // Obtain the current values of the joystick controllers.
        //
        // Note that x and y equal -1 when the joystick is pushed all of the way
        // forward (i.e. away from the human holder's body).
        //
        // The clip method guarantees the value never exceeds the range +-1.
        //
        // The DC motors are scaled to make it easier to control them at slower
        // speeds.
        //
        // The setPower methods write the motor power values to the DcMotor
        // class, but the power levels aren't applied until this method ends.
        //

        //
        // Manage the drive wheel motors.
        //
        float leftStickY = -gamepad1.left_stick_y;
        float leftStickX = -gamepad1.left_stick_x;

        // math.min and math.max compare 2 numbers and output the smallest and largest
        // thus capping drive speed to one
        // you will experience jankiness when you jump a 1 total in |x| + |y| as one wheel hits max speed
        // don't know how to solve that yet
        // 
        // update : realised there is a clip method, don't know how to use it though
        float minmaxleftdrive = Math.max(Math.min( leftStickY + leftStickX, 1), -1);
        float minmaxrightdrive = Math.max(Math.min( leftStickY - leftStickX, 1), -1);


        //
        // Manage the arm motor.
        //
        float l_gp2_left_stick_y = -gamepad2.left_stick_y;
        float l_left_arm_power = (float)scale_motor_power (l_gp2_left_stick_y);

        // party button
        // DANCE! but DANCE SAFELY IN AN OPEN AREA 
        float partytimer;
        // value assigned to Lb's currrent value
        boolean partybutton = gamepad1.left_bumper;
        
        if (partybutton==true){
            // sets a new random value on a clock of every 40 loop cycles
            partytimer++;
            if (partytimer == 40){
                partytimer = 0;
                float movement = Math.random();
            }
            // sets the bot spinning based on randomised speed
            float minmaxleftdrive = float movement;
            float minmaxrightdrive = -(float movement);
            
            //waves arm up and down randomly
            float 1_left_arm_power = float movement;
            
            //activates clapping claw every 10 (or 9) cycles
            if (  partytimer == 10 or  partytimer == 20 or partytimer == 39){
                // clock value to open and close the claw constantly
                boolean clap;
                // clap++ should alternate between true or false
                clap++;
                //opens when clap is true
                if (  clap = true){
                    m_hand_position (a_hand_position () - 0.05);
                }
                //closes when clap is true
                else if (clap = false){
                    m_hand_position (a_hand_position () + 0.05);
                }
                

            }
        }

        //setting drive powers
        float l_left_drive_power = (float)scale_motor_power (minmaxleftdrive);
        float l_right_drive_power = (float)scale_motor_power (minmaxrightdrive);
        set_drive_power (l_left_drive_power, l_right_drive_power);
        v_motor_left_arm.setPower (l_left_arm_power);

        //----------------------------------------------------------------------
        //
        // Servo Motors
        //
        // Obtain the current values of the gamepad 'x' and 'b' buttons.
        //
        // Note that x and b buttons have boolean values of true and false.
        //
        // The clip method guarantees the value never exceeds the allowable range of
        // [0,1].
        //
        // The setPosition methods write the motor power values to the Servo
        // class, but the positions aren't applied until this method ends.
        //
        if (gamepad2.x)
        {
            m_hand_position (a_hand_position () + 0.05);
        }
        else if (gamepad2.b)
        {
            m_hand_position (a_hand_position () - 0.05);
        }

        //
        // Send telemetry data to the driver station.
        //
        update_telemetry(); // Update common telemetry
        telemetry.addData ("10", "GP1 Left: " + l_gp1_left_stick_y);
        telemetry.addData ("11", "GP1 Right: " + l_gp1_right_stick_y);
        telemetry.addData ("12", "GP2 Left: " + l_gp2_left_stick_y);
        telemetry.addData ("13", "GP2 X: " + gamepad2.x);  //blah
        telemetry.addData ("14", "GP2 Y: " + gamepad2.b);

    } // PushBotManual::loop

} // PushBotManual
