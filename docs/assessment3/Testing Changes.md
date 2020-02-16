# Test Methods
- Updated paragraph 1 of Testing Methods to include explanation+example of why we use Mockito.
- More detail about Traceability Matrix
- Commented on using IntelliJ Statement Coverage for unit tests, outline why it's helpful and how we'd use the results to make further decisions.

# Test Report
- Updated statistics for FireTruckTest, FortressTest
- Added section for AlienTest

# FireTruckTest.java
- Updated attribute checking tests (e.g. differentSpeedTest) to include all new trucks (i.e. Attack, Tank)
- Added tiles/frame movement tests for new trucks
- Added damage to fortress tests for new trucks
- Added water reserve after attacking fortress for new trucks

# FortressTest
- Added attribute checking tests to include all new fortresses
- Added attackTruck() tests for all new fortresses
- Added on, before and after range boundary tests for all new fortresses

- Added AlienTest.java
# AlienTest
- Added tests for moving alien to tiles right, left, up and down.
- Added test for checking an alien will collide with a fireTruck along its patrol route
- Added test for checking an alien will not collide with a fireTruck along its patrol route

- Added MinigameTest.java
# MinigameTest
- Added test to ensure an entity's Rectangle is always updated to the location of the entity when it moves.
- Added tests for moving the fireTruck left and right along the screen.
- Added test for aliens moving down from the top of the screen
- Added test for water droplets moving up the screen when instantiated.

# System Test
- Removed section about system tests, as we didn't run them.

==========================

# Manual Tests
- Added tables to each test detailing Input, Expected Output, Actual Output, Pass/Fail
- Added a "Process" section
  - Extra  Tests added:
    - Attacking the first fortress starts the countdown timer.
    - ETs destroy fortress after first fortress is attacked
    - Minigame launches after second fortress is destroyed
    - Pressing "space" shoots a water droplet in the minigame.
    - Water droplets kill ETs upon collision in minigame
    - Minigame exits (lose) when ETs reach the bottom of the screen.
    - Minigame exits (win) when a player achieves a score of 100 points.
    - Destroyed fire station doesn't repair/refuel trucks
    - ET patrols attack fire truck when in range


==========================

# Traceability Matrix
- Added FORT_ATTACK_TRAINSTATION, FORT_ATTACK_MINSTER, FORT_ATTACK_SHAMBLES entries
- Added MAN_MINIGAME_LAUNCH, MAN_FORTRESS_IMPROVE, MAN_ATTACK_COUNTDOWN, MAN_PATROL_ATTACK, MAN_NO_REPAIR_REFILL entries.
