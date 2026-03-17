package org.scalatestplus.selenium

import org.scalatest._
import SharedHelpers.EventRecordingReporter
import org.scalatest.tagobjects.Slow

class DriverSpec extends JettySpec {

  describe("Tests grouped using Driver trait") {

    trait GoogleSearchSpec extends funspec.AnyFunSpecLike with concurrent.Eventually with concurrent.IntegrationPatience { this: WebBrowser with Driver =>

      describe("mock-google-search.html") {

        it("should change its title based on the term searched") {
          go to (host + "mock-google-search.html")
          clickOn("q")
          textField("q").value = "Cheese!"
          submit()
          // The mock search page dynamically sets the title based on the search term
          eventually(assert(pageTitle === "Cheese! - Google Search"))
          close()
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
      assert(scopeOpenedList(0).message == "mock-google-search.html")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "mock-google-search.html")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "mock-google-search.html should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "mock-google-search.html should change its title based on the term searched")
    }

    it("should work with Firefox", Slow) {
      // Cancel when Firefox is not available
      val s = try new GoogleSearchSpecWithFirefox catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "mock-google-search.html")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "mock-google-search.html")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "mock-google-search.html should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "mock-google-search.html should change its title based on the term searched")
    }

    it("should work with Internet Explorer", Slow) {
      // Cancel when Internet Explorer is not available
      val s = try new GoogleSearchSpecWithInternetExplorer catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "mock-google-search.html")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "mock-google-search.html")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "mock-google-search.html should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "mock-google-search.html should change its title based on the term searched")
    }

    it("should work with Safari", Slow) {
      // Cancel when Safari is not available
      val s = try new GoogleSearchSpecWithSafari catch { case e: Throwable => cancel(e) }
      val rep = new EventRecordingReporter
      s.run(None, Args(reporter = rep))
      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "mock-google-search.html")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "mock-google-search.html")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "mock-google-search.html should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "mock-google-search.html should change its title based on the term searched")
    }

    it("should work with Microsoft Edge", Slow) {
      // Debug information
      println(s"=== Edge Test Debug Info ===")
      println(s"OS: ${System.getProperty("os.name")}")
      println(s"OS Version: ${System.getProperty("os.version")}")
      println(s"OS Arch: ${System.getProperty("os.arch")}")
      println(s"Java Version: ${System.getProperty("java.version")}")
      println(s"Java Home: ${System.getProperty("java.home")}")

      // Cancel when Microsoft Edge is not available
      val s = try {
        println("Attempting to create GoogleSearchSpecWithEdge...")
        val spec = new GoogleSearchSpecWithEdge
        println("Successfully created GoogleSearchSpecWithEdge")
        spec
      } catch {
        case e: Throwable =>
          println(s"Failed to create GoogleSearchSpecWithEdge:")
          println(s"  Exception type: ${e.getClass.getName}")
          println(s"  Exception message: ${e.getMessage}")
          println(s"  Stack trace:")
          e.getStackTrace.take(10).foreach(st => println(s"    at $st"))
          cancel(e)
      }

      val rep = new EventRecordingReporter
      println("Running spec with EventRecordingReporter...")
      s.run(None, Args(reporter = rep))

      println(s"Test execution completed. Event counts:")
      println(s"  Scope opened: ${rep.scopeOpenedEventsReceived.size}")
      println(s"  Scope closed: ${rep.scopeClosedEventsReceived.size}")
      println(s"  Test started: ${rep.testStartingEventsReceived.size}")
      println(s"  Test succeeded: ${rep.testSucceededEventsReceived.size}")
      println(s"  Test failed: ${rep.testFailedEventsReceived.size}")
      println(s"  Test canceled: ${rep.testCanceledEventsReceived.size}")

      val scopeOpenedList = rep.scopeOpenedEventsReceived
      assert(scopeOpenedList.size == 1)
      assert(scopeOpenedList(0).message == "mock-google-search.html")
      val scopeClosedList = rep.scopeClosedEventsReceived
      assert(scopeClosedList.size == 1)
      assert(scopeClosedList(0).message == "mock-google-search.html")
      val testStartingList = rep.testStartingEventsReceived
      assert(testStartingList.size == 1)
      assert(testStartingList(0).testName == "mock-google-search.html should change its title based on the term searched")
      val testSucceededList = rep.testSucceededEventsReceived
      assert(testSucceededList.size == 1)
      assert(testSucceededList(0).testName == "mock-google-search.html should change its title based on the term searched")
      println("=== Edge Test Debug Info End ===")
    }
  }

}
