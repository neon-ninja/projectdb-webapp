<?php

use Behat\Behat\Context\ClosuredContextInterface,
    Behat\Behat\Context\TranslatedContextInterface,
    Behat\Behat\Context\BehatContext,
    Behat\Behat\Exception\PendingException;
use Behat\Gherkin\Node\PyStringNode,
    Behat\Gherkin\Node\TableNode;
    
use Behat\MinkExtension\Context\MinkContext;

//
// Require 3rd-party libraries here:
//
//   require_once 'PHPUnit/Autoload.php';
//   require_once 'PHPUnit/Framework/Assert/Functions.php';
//

/**
 * Features context.
 */
class FeatureContext extends MinkContext
{
    /**
     * Initializes context.
     * Every scenario gets it's own context object.
     *
     * @param array $parameters context parameters (set them up through behat.yml)
     */
    public function __construct(array $parameters)
    {
      // Initialize your context here
      $this->users = $parameters['users'];
    }

    /**
   * @Given /^I fill in the following <formdetails>$/
   *
   * http://www.whiteoctober.co.uk/blog/2012/09/12/behat-tablenodes-the-missing-manual/
   */
  public function iFillInTheFollowingFormdetails(TableNode $table) {
    
    $tableValues = $table->getHash();
    $element = $this->getSession()->getPage();
    if (empty($element)) {
      throw new Exception('Page not found');
    }

    foreach($tableValues as $formData) {
      
      switch($formData['field_type']) {
      
        case 'text':
            $element->fillField($formData['form_id'], $formData['value']);
          break;
        case 'check':
        case 'select':
          print $element->selectFieldOption($formData['form_id'], $formData['value']);
          break;

      }
    }
  }
  /**
   * @Then /^I wait (\d+) ms$/
   */
  public function iWaitMs($arg1) {
    sleep($arg1/1000.0);
  }
  
  /**
   * @Then /^I click "([^"]*)"$/
   */
  public function iClick($arg1)
  {
    $element = $this->getSession()->getPage();
    $button = $element->findButton($arg1);
    $button->click();
    //$redir = $button->getValue();
    //parent::visit($redir);
  }
  
  /**
  * @Then /^I wait until I see "([^"]*)"$/
  */
  public function iWaitUntilISee($arg1)
  {
    $timeWaited = 0;
    while ($timeWaited < 10) {
      if ($this->getSession()->getPage()->hasContent($arg1)) {
        return;
      } else {
        $timeWaited++;
        sleep(1);
      }
    }
    throw new Exception("Timed out waiting for $arg1 to appear");
  }
  
  /**
   * @When /^I\'m logged in as ([^"]*)$/
   */
  public function iMLoggedInAs($arg1)
  {
    $user = $this->users[$arg1];
    $this->getSession()->setRequestHeader("RemoteUser",$user);
  }
  
}