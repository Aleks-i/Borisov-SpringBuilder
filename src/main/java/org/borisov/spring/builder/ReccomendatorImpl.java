package org.borisov.spring.builder;

@Singleton
@Deprecated
public class ReccomendatorImpl implements Reccomendator {

    @InjectProperty("alcohol")
    private String drinkName;

    public ReccomendatorImpl() {
        System.out.println("Recomendator was created");
    }

    @Override
    public void reccomend() {
        System.out.println("drink " + drinkName);
    }
}
