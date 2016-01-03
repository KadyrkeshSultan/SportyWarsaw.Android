package net.azurewebsites.sportywarsaw.infrastructure;

import net.azurewebsites.sportywarsaw.activities.LoginActivity;
import net.azurewebsites.sportywarsaw.activities.MainActivity;
import net.azurewebsites.sportywarsaw.activities.RegisterActivity;
import net.azurewebsites.sportywarsaw.fragments.MeetingsTabFragment;
import net.azurewebsites.sportywarsaw.fragments.SportsFacilitiesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component injecting dependencies from {@link RestServicesModule}
 * and {@link ApplicationModule}
 *
 * @author Marcin Chudy
 */
@Singleton
@Component(modules = {RestServicesModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
    void inject(SportsFacilitiesFragment startupFragment);
    void inject(MeetingsTabFragment meetingsFragment);
}