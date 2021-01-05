package UseCases;
import Entities.*;
import Exceptions.DoesNotExistException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class AvailabilityChecker implements java.io.Serializable {
    /**
     * Class used to check if a user or room is available at a certain time
     * To use, instantiate a new availability checker and call methods
     */

    public AvailabilityChecker(){}

    /**
     * Checks if a speaker is available to give a talk
     * @param talk - Talk object, talk we seek to schedule
     * @param speaker- Speaker object, speaker we seek to schedule
     * @param tm - TalkManager object storing talks
     * @return true iff speaker is available to be scheduled
     * @throws DoesNotExistException if speaker or talk is not found
     */
    public boolean isSpeakerAvailable(Entities.Talk talk, Speaker speaker, TalkManager tm)
            throws DoesNotExistException {
        LocalTime time = talk.getStartTime();
        LocalTime time1 = time.plusMinutes(talk.getDuration());
        GregorianCalendar date = talk.getDate();
        for (String talkID: speaker.getHostingTalks()){
            if (isThereConflict(tm, time, time1, date, talkID)) return false;
        }
        int duration = talk.getDuration();
        return isUserAvailable(date, time, duration, speaker, tm);
    }

    /**
     * Checks if speaker is available for duration minutes at a given time
     * @param date - GregorianCalendar date to be checked
     * @param startTime - LocalTime time at which we want to check if speaker is available
     * @param duration - int, number of minutes we wish to check speaker is available for
     * @param speaker- Speaker object, speaker we wish to check
     * @param tm - TalkManager object, storing talks
     * @return true iff speaker is available to be scheduled
     * @throws DoesNotExistException if speaker is not found
     */
    public boolean isSpeakerAvailable(GregorianCalendar date, LocalTime startTime, int duration, Speaker speaker, TalkManager tm)
            throws DoesNotExistException {
        LocalTime endTime = startTime.plusMinutes(duration);
        for (String talkID : speaker.getHostingTalks()) {
            if (isThereConflict(tm, startTime, endTime, date, talkID)){return false;}
        }
        return isUserAvailable(date, startTime, duration, speaker, tm);
    }


    /**
     * Checks if room is available at the given date and time for the given duration
     * @param date Gregorian Calendar object representing date to check room's availability
     * @param time  LocalTime object - time at which to check room's availability
     * @param duration int - number of minutes we want room to be free
     * @param room Room object, room whose availability we wish to check
     * @param tm TalkManager storing talks
     * @return true iff room is available
     * @throws DoesNotExistException
     */

    public boolean isRoomAvailable(GregorianCalendar date, LocalTime time, int duration, Room room, TalkManager tm) throws DoesNotExistException {
        LocalTime time1 = time.plusMinutes(duration);
        if (time.compareTo(room.getClosingTime().minusMinutes(duration)) > 0 ||
                time.compareTo(room.getOpenTime()) < 0) {
            return false;
        }
        if(room.getSchedule().get(date) == null)
            return true;
        for (String talkID: room.getSchedule().get(date)) {
            Event event = tm.findTalk(talkID);
            LocalTime eventTime = event.getStartTime();
            LocalTime eventTime1 = event.getStartTime().plusMinutes(event.getDuration());
            if ((eventTime1.compareTo(time) > 0 && eventTime1.compareTo(time1) <= 0) ||
                    (eventTime.compareTo(time) >= 0 && eventTime.compareTo(time1) < 0)){
                return false;
            }
        }
        return true;
    }

    /**
     * Check if room is available to hold a given talk
     * @param talk - Talk to be checked
     * @param room - Entities.Room object, room to be checked if available
     * @param tm - UseCases.TalkManager object storing talks
     * @return true if and only if room is available at given time
     */

    public boolean isRoomAvailable(Event talk, Room room, TalkManager tm) throws DoesNotExistException {
        LocalTime time = talk.getStartTime();
        LocalTime time1 = time.plusMinutes(talk.getDuration());
        if (time.compareTo(room.getClosingTime().minusMinutes(talk.getDuration())) > 0 ||
                time.compareTo(room.getOpenTime()) < 0) {
            return false;
        }
        GregorianCalendar date = talk.getDate();
        if(room.getSchedule().get(date) == null)
            return true;
        for (String talkID: room.getSchedule().get(date)) {
            Event event = tm.findTalk(talkID);
            LocalTime eventTime = event.getStartTime();
            LocalTime eventTime1 = event.getStartTime().plusMinutes(event.getDuration());
            if ((eventTime1.compareTo(time) > 0 && eventTime1.compareTo(time1) <= 0) ||
                    (eventTime.compareTo(time) >= 0 && eventTime.compareTo(time1) < 0)){
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if the user is available at startTime on date for duration minutes
     * @param date GregorianCalendar object, the date we wish to check
     * @param startTime LocalTime object, the start time we wish to check
     * @param duration int, how many minutes we seek the user to be available for
     * @param user - User object, the user we wish to check
     * @param tm - TalkManager object, storing talks
     * @return true iff user is available
     * @throws DoesNotExistException if user is not found
     */
    public boolean isUserAvailable(GregorianCalendar date, LocalTime startTime, int duration, User user, TalkManager tm) throws DoesNotExistException {
        LocalTime time1 = startTime.plusMinutes(duration);
        for (String talkID: user.getRegisteredEvents()) {
            if (isThereConflict(tm, startTime, time1, date, talkID)) return false;
        }
        return true;
    }

    /**
     * Checks if the user is available to attend a given event
     * @param event - Event we want to check if user is available to attend
     * @param user - User whose availability to be checked
     * @param tm - TalkManager storing list of talks
     * @return true iff user is available to attend the talk
     * @throws DoesNotExistException if user or event is not found
     */
    public boolean isUserAvailable(Event event, User user, TalkManager tm) throws DoesNotExistException{
        GregorianCalendar date = event.getDate();
        LocalTime startTime = event.getStartTime();
        int duration = event.getDuration();
        return isUserAvailable(date, startTime, duration, user, tm);
    }

    private boolean isThereConflict(TalkManager tm, LocalTime time, LocalTime time1, GregorianCalendar date, String talkID) throws DoesNotExistException {
        Event event = tm.findTalk(talkID);
        LocalTime eventTime = event.getStartTime();
        LocalTime eventTime1 = eventTime.plusMinutes(event.getDuration());
        return (eventTime1.compareTo(time) > 0 && eventTime1.compareTo(time1) <= 0 && date.equals(event.getDate())) ||
        (eventTime.compareTo(time) >= 0 && eventTime.compareTo(time1) < 0 && date.equals(event.getDate()));
    }


}
