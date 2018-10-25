package ca.usherbrooke.pacman.controller;

import java.awt.event.KeyEvent;

public interface ICommand {

  void execute(KeyEvent keyEvent);
}
