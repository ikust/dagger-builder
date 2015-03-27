package co.ikust.daggerinjector.coffemaker;

import javax.inject.Inject;

/**
 * Created by ivan on 04/03/15.
 */
public class Termisiphon implements Pump {

    private final Heater heater;

    @Inject
    Termisiphon(Heater heater) {
        this.heater = heater;
    }


}
