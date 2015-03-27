package co.ikust.daggerinjector.coffemaker.module;

import co.ikust.daggerinjector.coffemaker.ElectricHeater;
import co.ikust.daggerinjector.coffemaker.Heater;
import co.ikust.daggerinjector.coffemaker.Pump;
import co.ikust.daggerinjector.coffemaker.Termisiphon;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ivan on 04/03/15.
 */
@Module
public class DripCoffeeModule {
    @Provides
    Heater provideHeater() {
        return new ElectricHeater();
    }

    @Provides
    Pump providePump(Termisiphon pump) {
        return pump;
    }
}
