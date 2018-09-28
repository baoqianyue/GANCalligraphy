package com.barackbao.sketchpad.utils

import android.graphics.Canvas
import com.barackbao.sketchpad.view.model.Model
import java.util.*

class HistoryUtil(private val canvas: Canvas){

    /**
     * the stack to save the history operation
     * to undo and redo
     */
    val mUndoStack = LinkedList<Model>()
    val mRedoStack = LinkedList<Model>()

    /**
     *  add the history to the undo stack and draw directly
     */
    fun addHistory(history:Model){
        mUndoStack.push(history)
        history.draw(canvas)
    }

    /**
     * undo the previous operation, if the model can be undo
     */
    fun undo(){
        if (mUndoStack.isNotEmpty()){
            CanvasUtil.clear(canvas)
            var history = mUndoStack.peek()
            if (history.hasUndo()){
                history.undo()
            } else{
                history = mUndoStack.pop()
                mRedoStack.push(history)
            }
            mUndoStack.forEach { it.draw(canvas) }
        }
    }
}