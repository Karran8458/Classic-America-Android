package com.hvac.classicamerice.POJOClass;

public class profileImage {


    /**
     * firstName : {"localized":{"en_US":"Bob"},"preferredLocale":{"country":"US","language":"en"}}
     * localizedFirstName : Bob
     * headline : {"localized":{"en_US":"API Enthusiast at LinkedIn"},"preferredLocale":{"country":"US","language":"en"}}
     * localizedHeadline : API Enthusiast at LinkedIn
     * vanityName : bsmith
     * id : yrZCpj2Z12
     * lastName : {"localized":{"en_US":"Smith"},"preferredLocale":{"country":"US","language":"en"}}
     * localizedLastName : Smith
     * profilePicture : {"displayImage":"urn:li:digitalmediaAsset:C4D00AAAAbBCDEFGhiJ"}
     */

    private FirstNameBean firstName;
    private String localizedFirstName;
    private HeadlineBean headline;
    private String localizedHeadline;
    private String vanityName;
    private String id;
    private LastNameBean lastName;
    private String localizedLastName;
    private ProfilePictureBean profilePicture;

    public FirstNameBean getFirstName() {
        return firstName;
    }

    public void setFirstName(FirstNameBean firstName) {
        this.firstName = firstName;
    }

    public String getLocalizedFirstName() {
        return localizedFirstName;
    }

    public void setLocalizedFirstName(String localizedFirstName) {
        this.localizedFirstName = localizedFirstName;
    }

    public HeadlineBean getHeadline() {
        return headline;
    }

    public void setHeadline(HeadlineBean headline) {
        this.headline = headline;
    }

    public String getLocalizedHeadline() {
        return localizedHeadline;
    }

    public void setLocalizedHeadline(String localizedHeadline) {
        this.localizedHeadline = localizedHeadline;
    }

    public String getVanityName() {
        return vanityName;
    }

    public void setVanityName(String vanityName) {
        this.vanityName = vanityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LastNameBean getLastName() {
        return lastName;
    }

    public void setLastName(LastNameBean lastName) {
        this.lastName = lastName;
    }

    public String getLocalizedLastName() {
        return localizedLastName;
    }

    public void setLocalizedLastName(String localizedLastName) {
        this.localizedLastName = localizedLastName;
    }

    public ProfilePictureBean getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePictureBean profilePicture) {
        this.profilePicture = profilePicture;
    }

    public static class FirstNameBean {
        /**
         * localized : {"en_US":"Bob"}
         * preferredLocale : {"country":"US","language":"en"}
         */

        private LocalizedBean localized;
        private PreferredLocaleBean preferredLocale;

        public LocalizedBean getLocalized() {
            return localized;
        }

        public void setLocalized(LocalizedBean localized) {
            this.localized = localized;
        }

        public PreferredLocaleBean getPreferredLocale() {
            return preferredLocale;
        }

        public void setPreferredLocale(PreferredLocaleBean preferredLocale) {
            this.preferredLocale = preferredLocale;
        }

        public static class LocalizedBean {
            /**
             * en_US : Bob
             */

            private String en_US;

            public String getEn_US() {
                return en_US;
            }

            public void setEn_US(String en_US) {
                this.en_US = en_US;
            }
        }

        public static class PreferredLocaleBean {
            /**
             * country : US
             * language : en
             */

            private String country;
            private String language;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }
        }
    }

    public static class HeadlineBean {
        /**
         * localized : {"en_US":"API Enthusiast at LinkedIn"}
         * preferredLocale : {"country":"US","language":"en"}
         */

        private LocalizedBeanX localized;
        private PreferredLocaleBeanX preferredLocale;

        public LocalizedBeanX getLocalized() {
            return localized;
        }

        public void setLocalized(LocalizedBeanX localized) {
            this.localized = localized;
        }

        public PreferredLocaleBeanX getPreferredLocale() {
            return preferredLocale;
        }

        public void setPreferredLocale(PreferredLocaleBeanX preferredLocale) {
            this.preferredLocale = preferredLocale;
        }

        public static class LocalizedBeanX {
            /**
             * en_US : API Enthusiast at LinkedIn
             */

            private String en_US;

            public String getEn_US() {
                return en_US;
            }

            public void setEn_US(String en_US) {
                this.en_US = en_US;
            }
        }

        public static class PreferredLocaleBeanX {
            /**
             * country : US
             * language : en
             */

            private String country;
            private String language;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }
        }
    }

    public static class LastNameBean {
        /**
         * localized : {"en_US":"Smith"}
         * preferredLocale : {"country":"US","language":"en"}
         */

        private LocalizedBeanXX localized;
        private PreferredLocaleBeanXX preferredLocale;

        public LocalizedBeanXX getLocalized() {
            return localized;
        }

        public void setLocalized(LocalizedBeanXX localized) {
            this.localized = localized;
        }

        public PreferredLocaleBeanXX getPreferredLocale() {
            return preferredLocale;
        }

        public void setPreferredLocale(PreferredLocaleBeanXX preferredLocale) {
            this.preferredLocale = preferredLocale;
        }

        public static class LocalizedBeanXX {
            /**
             * en_US : Smith
             */

            private String en_US;

            public String getEn_US() {
                return en_US;
            }

            public void setEn_US(String en_US) {
                this.en_US = en_US;
            }
        }

        public static class PreferredLocaleBeanXX {
            /**
             * country : US
             * language : en
             */

            private String country;
            private String language;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }
        }
    }

    public static class ProfilePictureBean {
        /**
         * displayImage : urn:li:digitalmediaAsset:C4D00AAAAbBCDEFGhiJ
         */

        private String displayImage;

        public String getDisplayImage() {
            return displayImage;
        }

        public void setDisplayImage(String displayImage) {
            this.displayImage = displayImage;
        }
    }
}
