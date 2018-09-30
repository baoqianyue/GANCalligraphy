package com.barackbao.sketchpad.utils

import android.graphics.*
import com.barackbao.sketchpad.view.model.BitmapModel
import com.barackbao.sketchpad.view.model.Model
import com.barackbao.sketchpad.view.model.PathModel
import java.util.*

class HistoryUtil(private val canvas: Canvas) {

    /**
     * the stack to save the history operation
     * to undo and redo
     */
    val mUndoStack = LinkedList<Model>()
    val mRedoStack = LinkedList<Model>()

    companion object {
        fun toHistory(paint: Paint, path: Path): Model = PathModel(paint, path)
        fun toHistory(bitmap: Bitmap, bounds: RectF): Model = BitmapModel(bitmap, bounds)
    }

    /**
     *  add the history to the undo stack and draw directly
     */
    fun addHistory(history: Model) {
        mUndoStack.push(history)
        history.draw(canvas)
    }

    /**
     * undo the previous operation, if the model can be undo
     */
    fun undo() {
        if (mUndoStack.isNotEmpty()) {
            CanvasUtil.clear(canvas)
            var history = mUndoStack.peek()
            if (history.hasUndo()) {
                history.undo()
            } else {
                history = mUndoStack.pop()
                mRedoStack.push(history)
            }
            mUndoStack.forEach { it.draw(canvas) }
        }
    }


    /**
     * redo the previous operation,if the model can be redo
     */
    fun redo() {
        if (mRedoStack.isNotEmpty()) {
            if (mUndoStack.isEmpty()) {
                val history = mRedoStack.pop()
                history.draw(canvas)
                mUndoStack.push(history)
            } else {
                val peek = mUndoStack.peek()
                if (peek.hasRedo()) {
                    peek.redo()
                    CanvasUtil.clear(canvas)
                    mUndoStack.forEach { it.draw(canvas) }
                } else {
                    val history = mRedoStack.pop()
                    history.draw(canvas)
                    mUndoStack.push(history)
                }
            }
        }
    }


    /**
     * Is there any extra operation available in this time
     */
    fun hasUndo(): Boolean = mUndoStack.isNotEmpty()


    fun hasRedo(): Boolean {
        return if (mRedoStack.isNotEmpty()) {
            true
        } else {
            if (mUndoStack.isNotEmpty()) {
                mUndoStack.peek().hasRedo()
            } else {
                false
            }
        }
    }

    /**
     * clear the redo stack
     */
    fun clearRedoStack() {
        mRedoStack.clear()
    }


    /**
     *  clear all
     */
    fun clear() {
        mUndoStack.clear()
        mRedoStack.clear()
        CanvasUtil.clear(canvas)
    }

    /**
     * clear the bottom cache layer canvas
     */
    fun clearCanvas() {
        CanvasUtil.clear(canvas)
    }


    /**
     * clear the bottom cache layer canvas and redraw the canvas
     */
    fun redrawCanvas() {
        clearCanvas()
        mUndoStack.forEach { it.draw(canvas) }
    }
}