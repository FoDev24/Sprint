package com.androiddevs.Sprint.repositories

import com.androiddevs.Sprint.db.Run
import com.androiddevs.Sprint.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDAO: RunDAO
) {
    suspend fun insertRun(run: Run) =runDAO.insertRun(run)

    suspend fun deleteRun(run: Run) =runDAO.deleteRun(run)


    fun getAllRunsSortedByDate()=runDAO.getAllRunsSortedByDate()

    fun getAllRunsSortedByDistance()=runDAO.getAllRunsSortedByDistance()

    fun getAllRunsSortedByTimeInMillis()=runDAO.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByAvgSpeed()=runDAO.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByCaloriesBurned()=runDAO.getAllRunsSortedByCaloriesBurned()


    fun getTotalAvgSpeed() = runDAO.getTotalAvgSpeed()

    fun getTotalDistance() = runDAO.getTotalDistance()

    fun getTotalCaloriesBurned() = runDAO.getTotalCaloriesBurned()

    fun getTotalTimesInMillis() = runDAO.getTotalTimeInMillis()
}