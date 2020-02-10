package com.tibco.cep.studio.mapper.ui.data.utils;


/**
 * Interface for validating names (i.e. xml names, java names, etc.)<br>
 * This is used by {@link NameValidatingDocument} as its validation interface.
 */
public interface DocumentNameValidator {

    /**
     * Is this a legal character in the name
     * @param character The character.
     * @param isFirstCharacter If it's the first character in the name.
     * @return True if it is legal.
     */
    public boolean isValidNameCharacter(char character, boolean isFirstCharacter);

    /**
     * Assuming the character was bad, get a suitable replacement.
     * @param character The bad character.
     * @param isFirstCharacter If the character was first in the name.
     * @return A suitable replacment string (could be just a single character)
     */
    public String getReplacementString(char character, boolean isFirstCharacter);

    /**
     * Gets the maximum length allowed by this name, or -1 to indicate no limit.
     * @return The maximum name length.
     */
    public int getMaximumLength();

    /**
     * Called after all other checks have been made (i.e. characters valid, length valid) to see if this
     * is reserved or already used, etc.
     * @param fullName The name to test.
     * @return Null if the string is a valid name, otherwise a string error message saying why it isn't.
     */
    public String isReservedName(String fullName);

    /**
     * Called to generate a new valid name XXXXXXXX WCETODO docme.
     * is reserved or already used, etc.
     * @param startingName The name to start with (the 'seed')..
     * @return Null if the string is a valid name, otherwise a string error message saying why it isn't.
     */
    public String makeValidName(String startingName);
}
