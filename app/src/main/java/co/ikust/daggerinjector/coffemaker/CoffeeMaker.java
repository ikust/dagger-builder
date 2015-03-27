package co.ikust.daggerinjector.coffemaker;

import javax.inject.Inject;

/**
 * Created by ivan on 04/03/15.
 */
public class CoffeeMaker {

    @Inject
    CoffeeMaker() {

    }

    @Inject Heater heater;
    @Inject Pump pump;
}
