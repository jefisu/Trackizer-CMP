package com.jefisu.trackizer.core.util

import platform.Foundation.NSError

fun NSError.toException() = Exception(localizedDescription)
