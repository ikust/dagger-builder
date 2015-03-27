package co.ikust.daggerinjector.coffemaker;

import co.ikust.daggerinjector.coffemaker.module.DripCoffeeModule;
import dagger.Component;

/**
 * Created by ivan on 04/03/15.
 */
@Component(modules = DripCoffeeModule.class)
public interface CoffeeShop {

    CoffeeMaker maker();
}
