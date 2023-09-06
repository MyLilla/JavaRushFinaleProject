package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;

import java.util.Set;

import static com.javarush.jira.login.internal.web.UserTestData.ADMIN_ID;

public class ProfileTestData {

    public static final Set<String> MAIL_NOTIFICATION = Set.of("one_day_before_deadline",
            "three_days_before_deadline", "two_days_before_deadline");

    public static final Set<ContactTo> CONTACTS = Set.of(new ContactTo("github", "adminGitHub"),
            new ContactTo("tg", "adminTg"),
            new ContactTo("vk", "adminVk"));
    public static final ContactTo contactTo = new ContactTo("github", "adminGitHub");

    public static final ProfileTo PROFILE_TO = new ProfileTo(ADMIN_ID, MAIL_NOTIFICATION, CONTACTS);

    public static final MatcherFactory.Matcher<ProfileTo> PROF_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(
            ProfileTo.class, "adminDisplayName", "admin@gmail.com", "adminFirstName");
}
