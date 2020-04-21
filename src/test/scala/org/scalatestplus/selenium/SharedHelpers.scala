/*
 * Copyright 2001-2013 Artima, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scalatestplus.selenium

import org.scalatest.{Assertions, Reporter}
import org.scalatest.events._

object SharedHelpers extends Assertions {

  object SilentReporter extends Reporter {
    def apply(event: Event): Unit = ()
  }

  // This now needs to be thread safe, because I'm setting it in one thread
  // and asserting using it from a different thread in Async tests.
  class EventRecordingReporter extends Reporter {
    private var eventList: List[Event] = List()
    def eventsReceived = synchronized { eventList.reverse }
    def testSucceededEventsReceived: List[TestSucceeded] = {
      synchronized {
        eventsReceived filter {
          case _: TestSucceeded => true
          case _ => false
        } map {
          case event: TestSucceeded => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def testStartingEventsReceived: List[TestStarting] = {
      synchronized {
        eventsReceived filter {
          case _: TestStarting => true
          case _ => false
        } map {
          case event: TestStarting => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    // Why doesn't this work:
    // for (event: TestSucceeded <- eventsReceived) yield event
    def infoProvidedEventsReceived: List[InfoProvided] = {
      synchronized {
        eventsReceived filter {
          case _: InfoProvided => true
          case _ => false
        } map {
          case event: InfoProvided => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def noteProvidedEventsReceived: List[NoteProvided] = {
      synchronized {
        eventsReceived filter {
          case _: NoteProvided => true
          case _ => false
        } map {
          case event: NoteProvided => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def alertProvidedEventsReceived: List[AlertProvided] = {
      synchronized {
        eventsReceived filter {
          case _: AlertProvided => true
          case _ => false
        } map {
          case event: AlertProvided => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def markupProvidedEventsReceived: List[MarkupProvided] = {
      synchronized {
        eventsReceived filter {
          case _: MarkupProvided => true
          case _ => false
        } map {
          case event: MarkupProvided => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def scopeOpenedEventsReceived: List[ScopeOpened] = {
      synchronized {
        eventsReceived filter {
          case _: ScopeOpened => true
          case _ => false
        } map {
          case event: ScopeOpened => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def scopeClosedEventsReceived: List[ScopeClosed] = {
      synchronized {
        eventsReceived filter {
          case _: ScopeClosed => true
          case _ => false
        } map {
          case event: ScopeClosed => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def scopePendingEventsReceived: List[ScopePending] = {
      synchronized {
        eventsReceived filter {
          case _: ScopePending => true
          case _ => false
        } map {
          case event: ScopePending => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def testPendingEventsReceived: List[TestPending] = {
      synchronized {
        eventsReceived filter {
          case _: TestPending => true
          case _ => false
        } map {
          case event: TestPending => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def testCanceledEventsReceived: List[TestCanceled] = {
      synchronized {
        eventsReceived filter {
          case _: TestCanceled => true
          case _ => false
        } map {
          case event: TestCanceled => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def testFailedEventsReceived: List[TestFailed] = {
      synchronized {
        eventsReceived filter {
          case _: TestFailed => true
          case _ => false
        } map {
          case event: TestFailed => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def testIgnoredEventsReceived: List[TestIgnored] = {
      synchronized {
        eventsReceived filter {
          case _: TestIgnored => true
          case _ => false
        } map {
          case event: TestIgnored => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def suiteStartingEventsReceived: List[SuiteStarting] = {
      synchronized {
        eventsReceived filter {
          case _: SuiteStarting => true
          case _ => false
        } map {
          case event: SuiteStarting => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def suiteCompletedEventsReceived: List[SuiteCompleted] = {
      synchronized {
        eventsReceived filter {
          case _: SuiteCompleted => true
          case _ => false
        } map {
          case event: SuiteCompleted => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def suiteAbortedEventsReceived: List[SuiteAborted] = {
      synchronized {
        eventsReceived filter {
          case _: SuiteAborted => true
          case _ => false
        } map {
          case event: SuiteAborted => event
          case _ => throw new RuntimeException("should never happen")
        }
      }
    }
    def apply(event: Event): Unit = {
      synchronized {
        eventList ::= event
      }
    }
  }
}

