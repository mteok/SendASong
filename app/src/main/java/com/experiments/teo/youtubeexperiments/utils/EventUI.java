package com.experiments.teo.youtubeexperiments.utils;

import de.greenrobot.event.EventBus;

/**
 * Created by teo on 4/8/15.
 */
public class EventUI {

    public static class Search {
        private Search() {
        }
    }

    public static void goSearch() {
        EventBus.getDefault().post(new Search());
    }

    public static class VideoPage {
        private VideoPage() {
        }
    }

    public static class Home {
        private Home() {
        }
    }

    public static class Friends {
        private Friends() {
        }
    }

    public static class Profile {
        private Profile() {
        }
    }

    public static void goProfile() {
        EventBus.getDefault().post(new Profile());
    }

    public static void goFriends() {
        EventBus.getDefault().post(new Friends());
    }

    public static void goHome() {
        EventBus.getDefault().post(new Home());
    }

    public static void launchVideo() {
        EventBus.getDefault().post(new VideoPage());
    }

    public static void startFBLogin() {
        EventBus.getDefault().post(new FBLogin());
    }

    public static class FBLogin {
        private FBLogin() {
        }
    }

    public static class Share {
        private Share() {
        }
    }

    public static void goShare() {
        EventBus.getDefault().post(new Share());
    }
}
