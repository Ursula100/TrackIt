package ie.setu.mobileassignment

import ie.setu.mobileassignment.utils.EventValidation
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class EventValidationTest {

    private var today: LocalDate = LocalDate.now()
    private var pastDate: LocalDate = LocalDate.parse("2023-05-11")
    private var futureDate: LocalDate = LocalDate.parse("2027-10-18")

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `Returns false if the second localDate comes before the first`(){
        assertFalse(EventValidation.datesValid(today, pastDate))
    }

    @Test
    fun `Returns true if the second localDate is the same as the first`(){
        assertTrue(EventValidation.datesValid(today, today))
    }

    @Test
    fun `Returns true if the second localDate later than the first`(){
        assertTrue(EventValidation.datesValid(today, futureDate))
    }

}