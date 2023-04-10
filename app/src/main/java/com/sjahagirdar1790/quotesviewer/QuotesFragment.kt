/*
This Kotlin code defines a Fragment class that displays a quote in a TextView and allows the user
to swipe up or down to display the next or previous quote. It also registers the TextView for context
menu handling and provides methods for saving and restoring the state of the Fragment.
*/
package com.sjahagirdar1790.quotesviewer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sjahagirdar1790.quotesviewer.R
import kotlin.math.abs

// Define a constant value for the key of the quote state in the Bundle
const val QUOTE_STATE = "quoteState"

// Define the main Fragment class
class QuotesFragment : Fragment() {

    // Define two variables for storing touch event and quote index
    private var initTouchY = 0
    private var quoteIndex = 0

    // Define a variable for storing the reference to the TextView displaying the quote
    private lateinit var quoteTextView: TextView

    // Define a lazy initialization of the quotes array using the resources
    private val quotesArray by lazy { resources.getStringArray(R.array.quotes) }

    // Define a companion object with a factory method for creating the Fragment instance
    companion object {
        fun newInstance() = QuotesFragment()
    }

    // Override the onCreateContextMenu() method to inflate the context menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.context_menu, menu)
    }

    // Override the onCreateView() method to inflate the Fragment view
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the parent view using the specified layout resource
        val parentView = inflater.inflate(R.layout.fragment_quote, container, false)
        // Retrieve the reference to the quote TextView and store it in a variable
        quoteTextView = parentView.findViewById(R.id.quoteTextView)
        // Register the TextView for context menu handling
        registerForContextMenu(quoteTextView)

        // Add a touch listener to the parent view to allow swiping the quote text up and down
        parentView.setOnTouchListener { v, event ->
            var returnVal = true
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initTouchY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val y = event.y.toInt()
                    // If the vertical displacement exceeds 300 pixels, update the quote index and display the new quote
                    if (abs(y - initTouchY) >= 300) {
                        quoteIndex += if (y > initTouchY) -1 else 1
                        updateQuote()
                        initTouchY = y
                    }
                }
                else -> returnVal = false
            }
            returnVal
        }

        return parentView
    }

    // Override the onViewCreated() method to restore the state of the Fragment and display the current quote
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // If there is a saved instance state, restore the quote index from it
        if (savedInstanceState != null) {
            quoteIndex = savedInstanceState.getInt(QUOTE_STATE)
        }
        // Display the current quote
        updateQuote()
    }

    // Override the onSaveInstanceState() method to save the current quote index in the Bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(QUOTE_STATE, quoteIndex)
    }

    // Override the onContextItemSelected() method to handle the context menu selection
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.next -> {
                quoteIndex++
                updateQuote()
                true
            }
            R.id.prev -> {
                quoteIndex--
                updateQuote()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    /**
    This method updates the quote displayed in the TextView.
    If the quote index is out of bounds of the quotesArray, an AlertDialog is shown indicating that
    there are no more quotes.
    Otherwise, the quoteTextView's text is set to the current quote in quotesArray.
     */
    private fun updateQuote() {
        if (quoteIndex < 0 || quoteIndex >= quotesArray.size) {
            AlertDialog.Builder(requireContext())
                .setTitle("No quotes above")
                .setMessage("Click Dismiss to continue.")
                .setPositiveButton("DISMISS") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            return
        }
        quoteTextView.text = quotesArray[quoteIndex]
    }
}
