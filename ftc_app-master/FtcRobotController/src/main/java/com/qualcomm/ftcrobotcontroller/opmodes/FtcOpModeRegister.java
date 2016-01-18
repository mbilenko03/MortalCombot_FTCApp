package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBTeleOp;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBAutonomousBlueOp;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.pushbot.PBAutonomousRedOp;
import com.qualcomm.ftcrobotcontroller.opmodes.mortalcombot.custombot.CBTeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

public class FtcOpModeRegister implements OpModeRegister {

  public void register(OpModeManager manager) {

      //PushBot OpModes
      manager.register("PushBot TeleOp", PBTeleOp.class);
      manager.register("[Blue]PushBot AutonomousOp", PBAutonomousBlueOp.class);
      manager.register("[Red]PushBot AutonomousOp", PBAutonomousRedOp.class);

      //CustomBot OpModes
      manager.register("CustomBot TeleOp", CBTeleOp.class);

      //manager.register("EncoderTest", EncoderTest.class);
  }
}
