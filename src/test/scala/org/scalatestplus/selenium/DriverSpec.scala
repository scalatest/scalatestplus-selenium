package org.scalatestplus.selenium

import org.scalatest._
import SharedHelpers.EventRecordingReporter
import org.scalatest.tagobjects.Slow

class DriverSpec extends funspec.AnyFunSpec {

  describe("Tests grouped using Driver trait") {

    trait GoogleSearchSpec extends funspec.AnyFunSpecLike with concurrent.Eventually { this: WebBrowser with Driver =>

      describe("google.com") {

        it("should change its title based on the term searched") {
          // Cancel test when cannot access google.com
          try goTo("https://www.google.com") catch { case e: Throwable => cancel(e) }
          try {
            clickOn("q")
            textField("q").value = "Cheese!"
            submit()
            // Google's search is rendered dynamically with JavaScript.
            eventually(assert(pageTitle === "Cheese! - Google Search"))
          }
          finally close()
        }

      }

    }

    class GoogleSearchSpecWithChrome extends GoogleSearchSpec with Chrome
    class GoogleSearchSpecWithFirefox extends GoogleSearchSpec with Firefox
    class GoogleSearchSpecWithInternetExplorer extends GoogleSearchSpec with InternetExplorer
    class GoogleSearchSpecWithSafari extends GoogleSearchSpec with Safari
    class GoogleSearchSpecWithEdge extends GoogleSearchSpec with Edge

    it("should work with Chrome", Slow) {
      // Cancel when Chrome is not available
      val s = try new GoogleSearchSpecWithChrome catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "google.com")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "google.com")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "google.com should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "google.com should change its title based on the term searched")
    }

    it("should work with Firefox", Slow) {
      // Cancel when Firefox is not available
      val s = try new GoogleSearchSpecWithFirefox catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "google.com")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "google.com")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "google.com should change its title based on the term searched")
      println("####rep.testFailedEventsReceived: " + rep.testFailedEventsReceived)
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "google.com should change its title based on the term searched")
    }

    it("should work with Internet Explorer", Slow) {
      // Cancel when Internet Explorer is not available
      val s = try new GoogleSearchSpecWithInternetExplorer catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "google.com")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "google.com")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "google.com should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "google.com should change its title based on the term searched")
    }

    it("should work with Safari", Slow) {
      // Cancel when Safari is not available
      val s = try new GoogleSearchSpecWithSafari catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "google.com")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "google.com")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "google.com should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "google.com should change its title based on the term searched")
    }

    it("should work with Microsoft Edge", Slow) {
      // Cancel when Microsoft Edge is not available
      val s = try new GoogleSearchSpecWithEdge catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "google.com")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "google.com")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "google.com should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "google.com should change its title based on the term searched")
    }
  }

}
